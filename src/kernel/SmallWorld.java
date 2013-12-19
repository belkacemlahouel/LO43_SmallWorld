package kernel;

import gui.*;
import java.util.ArrayList;

/*
 * Class containing the set of rules for the SmallWorld
 * There's a Board, and the methods on the board for the management of this "SmallWorld" application
*/

public class SmallWorld extends Thread {
	private Board small_world;
	private ArrayList<Tribe> tribeList;
	private ArrayList<Resource> res; // At the beginning, resources of the Board, it doesn't belong to Individuals
	private SmallWorldGUI gui;
	
	/*
	 *	Method implementing the reaches for Human moves for Humans
	 *	For Humans: reach = 1. So they move from one Position to another, next to it
	 *	But wa could imagine species, for which the reach is bigger (faster Individuals), how would I implement it?
	 *	TODO add Positions that cannot be crossed...
	*/
	public Position getBestNextPosition (Individual tmp, Position final_pos) { // or ArrayLIst<Position> with the complete path...
		return small_world.getNextPosition (tmp, final_pos);
	}
	
	public Position getBestNextPosition (Individual tmp) {
		Position final_pos = tmp.getAimPosition ();
		if (final_pos == null || tmp.getPosition().equals(final_pos)) {
			final_pos = small_world.randPosition(); // get Position, in function of the type of the Individual (inside)
			tmp.setAimPosition (final_pos); // Random Position on this Board
		}
		
		return small_world.getNextPosition (tmp, final_pos);
	}

	public SmallWorld () {

		super ("Small World"); // Construction of the Thread
		
		tribeList = new ArrayList<Tribe>();
		res = new ArrayList<Resource> ();
		
		Tribe team_1 = new Tribe();
		Tribe team_2 = new Tribe();
		team_1.setTribeIndex(1);
		team_2.setTribeIndex(2);
		tribeList.add(team_1);
		tribeList.add(team_2);
	}
	
	public SmallWorldGUI getGui() {
		return gui;
	}

	public void setGui(SmallWorldGUI gui) {
		this.gui = gui;
	}

	public void setBoard(Board b) {
		small_world = b;
	}
	
	public Board getBoard(){
		return small_world;
	}
	
	public Individual getFirstEnnemySamePos (Individual e) { // Finding the ennemies (Individuals) on the same position
		ArrayList<Element> tmp_elementsList = small_world.get(e.getPosition()).getElementsList();
		if (!tmp_elementsList.isEmpty ()) {
			for (Element tmp_e : tmp_elementsList) {
				if (tmp_e instanceof Individual && !areFriends ((Individual)tmp_e, e)) { // e and tmp_e (Humans) are in two different teams
					return (Individual) tmp_e; // TODO
				}
			}
		}
		return null;
	}
	
	// Finding the ennemies (Humans) or Resources on the same position than e; Humans > Resources
	public Element getFirstElementSamePos (Individual tmp) {
		Resource tmp_r = null;
		ArrayList<Element> tmp_elementsList = small_world.get(tmp.getPosition()).getElementsList();
		if (!tmp_elementsList.isEmpty ()) {
			for (Element tmp_e : tmp_elementsList) {
				if (tmp_e instanceof Individual && !areFriends ((Individual)tmp_e, tmp)) {
					return (Individual)tmp_e;
				} else if (tmp_r == null && tmp_e instanceof Resource) { // first condition useless
					tmp_r = (Resource) tmp_e;
				}
			}
		}
		return tmp_r; // either returns null, or the reference on the first Resource met in the list
	}
	
	public boolean areFriends (Individual e1, Individual e2) { // testing if two Individuals are in the same team or not
		for(int i=0;i<tribeList.size();i++)
		{
			if(tribeList.get(i).getPopulation().contains(e1) && tribeList.get(i).getPopulation().contains(e2))
				return true;
		}
		return false;
	}

	public synchronized void run () {
		
		Individual tmp;
		Position tmp_pos, rand_pos;
		boolean has_played = false;

		// We should alternate between teams, not making all Individuals from one Tribe play after the another...
		while (tribeList.size() > 1) {
			for (int i=0 ; i<tribeList.size() ; i++) {
				for( int j=0 ; j<tribeList.get(i).getPopulation().size() ; j++) {
					has_played = false;
					tmp = tribeList.get(i).getPopulation().get(j);

					Individual tmp_ind = getFirstEnnemySamePos (tmp);
					if (tmp_ind != null) {
						tmp.attack (tmp_ind);
						buryDeads (tmp.getPosition ());
						has_played = true;
						System.out.print ("1");
					}

					// Implement the aim_position here
					// tmp_pos = getBestNextPosition(tmp, rand_pos);
					tmp_pos = getBestNextPosition (tmp);
					move (tmp, tmp_pos);
					
					if (!has_played) {
						tmp.attack(getFirstElementSamePos (tmp)); // if getFirstEnnemy != null, attack & has played = true
						buryDeads (tmp.getPosition ());
						System.out.print ("2");
					}
					
					System.out.println ();
				}
			}
			
			gui.updateMapPanel();
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println ("\n\n\tEnd of Small World...");
		// Are the dead tribes (defeated one) removed?
	}
		
		
	// TODO EXTRA BULLSHIT
	public synchronized void buryDeads (Position p) {
		small_world.get(p).buryDeads(); // burying deads in that Position, which leads us to the Case from the Board
		
		for(int i=0;i<tribeList.size();i++)
		{
			for(int j=0;j<tribeList.get(i).getPopulation().size();j++)
			{
				
				if(tribeList.get(i).getPopulation().get(j).isDead())
					tribeList.get(i).getPopulation().remove(j);
			}
		}
	}

	public synchronized void move (Individual tmp, Position new_pos) { // synchronized?
		if (!tmp.getPosition().equals(new_pos)) {
			small_world.get(tmp.getPosition ()).remove (tmp);
			tmp.setPosition (new_pos);
			small_world.get(new_pos).add (tmp);
		}
	}
	
	/* This method adds the specified individual in the specified tribe */
	
	public void addIndividual(Tribe t,Individual i) {
		this.tribeList.get(tribeList.indexOf(t)).getPopulation().add(i);
		this.gui.getMap().getIndivList().add(new ElementGUI(i));
	}
	
	public void addIndividual(Individual i,int tribeIndex) {
		tribeList.get(tribeIndex).getPopulation().add(i);
		this.gui.getMap().getIndivList().add(new ElementGUI(i));
	}

	@Override
	public String toString () {
		return "" + small_world;
	}

	
	public void addres(Resource s){
		res.add(s);
		this.gui.getMap().getResList().add(new ElementGUI(s));
	}
	
	public Tribe getTribe(int n) {
		return tribeList.get(n);
	}

	public ArrayList<Resource> getRes() {
		return res;
	}

	public void setRes(ArrayList<Resource> res) {
		this.res = res;
	}

	public ArrayList<Tribe> getTribeList() {
		return tribeList;
	}

	public void setTribeList(ArrayList<Tribe> tribeList) {
		this.tribeList = tribeList;
	}
}
