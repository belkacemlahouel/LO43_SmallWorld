package kernel;

import gui.*;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Class containing the set of rules for the SmallWorld
 * There's a Board, and the methods on the board for the management of this "SmallWorld" application
*/

public class SmallWorld extends Thread {
	
	private Board small_world;
	private ArrayList<Tribe> tribe_list;
	private ArrayList<Resource> resources; // At the beginning, resources of the Board, it doesn't belong to Individuals
	private SmallWorldGUI gui;
	
	
	
	
	
	/*
	 *	Method implementing the reaches for Human moves for Humans
	 *	For Humans: reach = 1. So they move from one Position to another, next to it
	 *	But wa could imagine species, for which the reach is bigger (faster Individuals), how would I implement it?
	 *	TODO add Positions that cannot be crossed...
	*/
	/*public Position getNextPosition (Individual tmp, Position final_pos) { // or ArrayLIst<Position> with the complete path...
		return small_world.getNextPosition (tmp, final_pos);
		* 
		* Should each Individual change its objectives at each loop?
	}*/
	
	public Position getBestNextPosition (Individual tmp) {
		Position final_pos = tmp.getAimPosition ();
		if (final_pos == null || tmp.getPosition().equals(final_pos)) {
			// final_pos = small_world.randPosition(); // get Position, in function of the type of the Individual (inside)
			// tmp.setAimPosition (final_pos); // Random Position on this Board
			final_pos = tmp.newAim (this);
		}
		
		return small_world.getNextPosition (tmp, tmp.newAim(this));
	}

	public SmallWorld () {

		super ("Small World"); // Construction of the Thread
		
		tribe_list = new ArrayList<Tribe> ();
		resources = new ArrayList<Resource> ();
		
		tribe_list.add(new Tribe ());
		tribe_list.add(new Tribe ());

		// resources.add (new Food (small_world.get(5, 7).getPosition (), "R.0"));
	}
	
	public SmallWorldGUI getGui() {
		return gui;
	}

	public void setGui (SmallWorldGUI gui) {
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
		for(int i=0;i<tribe_list.size();i++)
		{
			if(tribe_list.get(i).getPopulation().contains(e1) && tribe_list.get(i).getPopulation().contains(e2))
				return true;
		}
		return false;
	}
	
	private int maxSizeTribeList () {
		int tmp = 0;
		for (Tribe e : tribe_list) {
			if (e != null && !e.getPopulation().isEmpty()) tmp = Math.max (e.getPopulation().size(), tmp);
		}
		return tmp;
	}
	
	public int nbRemainingTribes () {
		int tmp = 0;
		for (Tribe e: tribe_list) {
			if (e != null && !e.getPopulation().isEmpty()) ++tmp;
		}
		return tmp;
	}
	
	@Override
	public synchronized void run () {
		
		Individual tmp;
		Position tmp_pos; // , rand_pos;
		boolean has_played;

		// We should alternate between teams, not making all Individuals from one Tribe play after the another...
		/*while (tribe_list.size() > 1) {
			for (int i=0 ; i<tribe_list.size() ; i++) {
				for( int j=0 ; j<tribe_list.get(i).getPopulation().size() ; j++) {*/
		while (nbRemainingTribes () > 1) {
			for (int i=0 ; i<maxSizeTribeList() ; i++) {
				for (int j=0 ; j<tribe_list.size() ; ++j) {
					if (tribe_list.get(j).getPopulation().size()>i) {
						has_played = false;
						tmp = tribe_list.get(j).getPopulation().get(i);

						Individual tmp_ind = getFirstEnnemySamePos (tmp);
						if (tmp_ind != null) {
							tmp.attack (tmp_ind);
							buryDeads (tmp.getPosition ());
							has_played = true;
							// System.out.print ("1");
						}

						tmp_pos = getBestNextPosition (tmp);
						move (tmp, tmp_pos);

						if (!has_played) {
							tmp.attack(getFirstElementSamePos (tmp));
							buryDeads (tmp.getPosition ());
							// System.out.print ("2");
						}

						// System.out.println ();
					}
					
					System.out.println ();
				}
			}
			
			// System.out.println ("" + this);
			// System.out.println ("\n\t########################################\n");
			
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
	
	// Or we could just check after each action and bury deads on this Position...
	public synchronized void buryDeads (Position p) {
		small_world.get(p).buryDeads(); // burying deads in that Position, which leads us to the Case from the Board
		
		for (Tribe t : tribe_list) {
			Iterator<Individual> it = t.getPopulation().iterator();
			while (it.hasNext()) {
				Individual x = it.next();
				if (x.isDead()) {
					it.remove();
					return ; // assuming there is not two identical Individuals
				}
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
	
	@Override
	public String toString () {
		return "" + small_world;
	}

	
	
	
	
	// This method adds the Individual to the Tribe (in parameters)
	
	public void addResource (Resource r) {
		resources.add (r);
		this.gui.getMap().getResList().add(new ElementGUI(r)); // TODO
	}
	
	public void addIndividual(Tribe t,Individual i) {
		this.tribe_list.get(tribe_list.indexOf(t)).getPopulation().add(i);
		this.gui.getMap().getIndivList().add(new ElementGUI(i));
	}
	
	public void addIndividual(Individual i,int tribeIndex) {
		tribe_list.get(tribeIndex).getPopulation().add(i);
		this.gui.getMap().getIndivList().add(new ElementGUI(i));
	}
	
	public Tribe getTribeAt (int index) {
		return tribe_list.get(index);
	}
	
	public ArrayList<Tribe> getTribeList () {
		return tribe_list;
	}
}
