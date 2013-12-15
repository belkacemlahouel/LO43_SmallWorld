
/*
 * Class containing the set of rules for the SmallWorld
 * There's a Board, and the methods on the board for the management of this "SmallWorld" application
*/

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class SmallWorld extends Thread {
	private Board small_world;
	private ArrayList<Human> team_1, team_2;
	private ArrayList<Resource> res;
	private SmallWorldGUI gui;
	
	/*
	 *	Method implementing the reaches for Human moves for Humans
	 *	For Humans: reach = 1. So they move from one Position to another, next to it
	 *	But wa could imagine species, for which the reach is bigger (faster Individuals), how would I implement it?
	 *	TODO add Positions that cannot be crossed...
	*/
	public Position getBestNextPosition (Human e, Position final_pos) { // or ArrayLIst<Position> with the complete path...
		return small_world.getNextPosition (e, final_pos);
	}

	//public ArrayList<Position> getPossiblePositions (Human e) {
		//return small_world(e.getReach ()); // TODO add Cases that cannot be crossed!
	//} // switch to Individual after that...
	
	public SmallWorld () {

		super ("Small World"); // Construction of the Thread
		
		team_1 = new ArrayList<Human> (0);
		team_2 = new ArrayList<Human> (0);
		res = new ArrayList<Resource> (0);

		res.add(new Resource(new Position(5,7),"rock"));

		/*for (int i=0 ; i<3 ; ++i) { // These initializations should be done after parsing the XML file
			// Placing the Humans from team_1 on the first line, the Humans from team_2 on the last line + adding them to the Cases on the Board (small_world)
			team_1.add(new Human (small_world.get(0, i).getPosition(), "1."+(i+1)));
				small_world.get(0, i).add(team_1.get(i));
			team_2.add(new Human (small_world.get(2, i).getPosition(), "2."+(i+1)));
				small_world.get(2, i).add(team_2.get(i));
			res.add(new Resource (small_world.get(1, i).getPosition(), "R."+(i+1)));
				small_world.get(1, i).add(res.get(i));
			}*/
		
		gui = new SmallWorldGUI (this);
	}
	
	public void setBoard(Board b) {
		small_world = b;
	}
	
	public void addres(Resource s){
		res.add(s);
	}
	
	public Board getBoard(){
		return small_world;
	}
	
	public Human getFirstEnnemySamePos (Human e) { // Finding the ennemies (Humans) on the same position than e
		ArrayList<Element> tmp_elementsList = small_world.get(e.getPosition()).getElementsList();
		if (!tmp_elementsList.isEmpty ()) {
			for (Element tmp_e : tmp_elementsList) {
				if (tmp_e instanceof Human && !areFriends ((Human)tmp_e, e)) { // e and tmp_e (Humans) are in two different teams
					return (Human) tmp_e;
				}
			}
		}
		return null;
	}
	
	public Element getFirstElementSamePos (Human e) { // Finding the ennemies (Humans) or Resources on the same position than e; Humans > Resources
		Resource tmp_r = null;
		ArrayList<Element> tmp_elementsList = small_world.get(e.getPosition()).getElementsList();
		if (!tmp_elementsList.isEmpty ()) {
			for (Element tmp_e : tmp_elementsList) {
				if (tmp_e instanceof Human && !areFriends ((Human)tmp_e, e)) {
					return (Human)tmp_e;
				} else if (tmp_r == null && tmp_e instanceof Resource) {
					tmp_r = (Resource) tmp_e;
				}
			}
		}
		return tmp_r; // either returns null, or the reference on the first Resource met in the list
	}
	
	public boolean areFriends (Human e1, Human e2) { // testing if two Humans are in the same team or not, assuming we just have two teams (of Humans) for the moment
		return (team_1.contains(e1) && team_1.contains(e2)) ||
				(team_2.contains(e1) && team_2.contains(e2));
	}

	public synchronized void run () {
		
		Human tmp;
		Position tmp_pos, rand_pos;

		while (!team_1.isEmpty() && !team_2.isEmpty()) {
			for (int i=0 ; i<Math.max(team_1.size(), team_2.size()) ; ++i) {
				if (i >= 0 && i < team_1.size()) {
					tmp = team_1.get(i);
					rand_pos = small_world.randPosition ();
					tmp_pos = getBestNextPosition(tmp, rand_pos); // tmp_pos: an intermediary following the aim (randPosition)
					System.out.println ("" + tmp + "\t" + rand_pos + "\t" + tmp_pos);
					move (tmp, tmp_pos);
					// move (tmp, small_world.randPosition ());
					// Attacking the other people (Element) on this case, if they are from another team...
					tmp.attack(getFirstElementSamePos (tmp));
					buryDeads (tmp.getPosition ());
				}
				
				if (i >= 0 && i < team_2.size()) {
					tmp = team_2.get(i);
					rand_pos = small_world.randPosition ();
					tmp_pos = getBestNextPosition(tmp, rand_pos);
					System.out.println ("" + tmp + "\t" + rand_pos + "\t" + tmp_pos);
					move (tmp, tmp_pos);
					// move (tmp, small_world.randPosition ()); // small world is the BOARD
					tmp.attack(getFirstElementSamePos (tmp));
					small_world.get(tmp.getPosition ()).buryDeads ();
					buryDeads (tmp.getPosition ());
				}
			}
			//System.out.println ("" + this);
			//System.out.println ("\n\t########################################\n");
			
			gui.updateMapPanel();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println ("\n\n\tEnd of Small World...");
		if (!team_2.isEmpty ()) System.out.println ("\tTeam 2 wins!");
		else if (!team_1.isEmpty ()) System.out.println ("\tTeam 1 wins!");
		else System.out.println ("\tNobody wins!");
	}
	
	public synchronized void buryDeads (Position p) {
		small_world.get(p).buryDeads(); // burying deads (Humans only, for the moment) in that Position, which leads us to the Case from the Board
		
		Iterator<Human> it = team_1.iterator();
		while (it.hasNext()) {
			Human x = it.next();
			if (x.isDead()) {
				it.remove();
				return ; // assuming there's only one instance Human, all differents...
			}
		}
		it = team_2.iterator();
		while (it.hasNext()) {
			Human x = it.next();
			if (x.isDead()) {
				it.remove();
				return ; // assuming there's only one instance Human, all differents...
			}
		}
	}

	public synchronized void move (Human e, Position new_pos) { // synchronized?
		if (!e.getPosition().equals(new_pos)) {
			small_world.get(e.getPosition ()).remove (e);
			e.setPosition (new_pos);
			small_world.get(new_pos).add (e);
		}
	}
	
	public void addHuman()
	{
		Human h = new Human(small_world.randPosition(),"human");
		this.team_1.add(h);
		this.gui.getPan2().getHumList().add(new ElementGUI(h));
	}

	public String toString () {
		return "" + small_world;
	}

	public ArrayList<Human> getTeam_1() {
		return team_1;
	}

	public void setTeam_1(ArrayList<Human> team_1) {
		this.team_1 = team_1;
	}

	public ArrayList<Resource> getRes() {
		return res;
	}

	public void setRes(ArrayList<Resource> res) {
		this.res = res;
	}
	
	public void addTeam1 (Human h) {
		team_1.add(h);
		this.gui.getPan2().getHumList().add(new ElementGUI(h));

	}
	
	public void addTeam2 (Human h) {
		team_2.add(h);
	}
}
