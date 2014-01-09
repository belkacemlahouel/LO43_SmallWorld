package kernel;

import kernel.resources.Resource;
import kernel.individuals.Individual;
import gui.*;
import java.util.ArrayList;
import java.util.Iterator;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 * Class containing the set of rules for the SmallWorld
 * There's a Board, and the methods on the board for the management of this "SmallWorld" application
 * It also inheritates a Thread, because this class manages the whole game
 */

public class SmallWorld extends Thread {
	
	private Board small_world;
	private ArrayList<Tribe> tribe_list;
	private ArrayList<Resource> resources; // At the beginning, resources are on the Board, it doesn't belong to Individuals
	private SmallWorldGUI gui;
	
	public SmallWorld () {
		super ("Small World"); // Construction of the Thread
		
		tribe_list = new ArrayList<Tribe> (0);
		resources = new ArrayList<Resource> (0);
	}
	
	/*
	 * Forces the Individual to compute a new aim if needed
	 * It computes also the intermediary to this aim
	 */
	public Position getBestNextPosition (Individual tmp) {
		Position final_pos = tmp.getAimPosition ();
		if (final_pos == null || tmp.getPosition().equals(final_pos)) {
			final_pos = tmp.newAim (this);
		}
		return small_world.getNextPosition (tmp, tmp.newAim(this));
	}

	/*
	 * Finding the ennemies (Individuals) on the same position
	 */
	public Individual getFirstEnnemySamePos (Individual e) {
		ArrayList<Element> tmp_elementsList = small_world.get(e.getPosition()).getElementsList();
		if (!tmp_elementsList.isEmpty ()) {
			for (Element tmp_e : tmp_elementsList) {
				if (tmp_e instanceof Individual && !areFriends ((Individual)tmp_e, e)) {
					return (Individual) tmp_e;
				}
			}
		}
		return null;
	}
	
	/*
	 * Finding the ennemies (Humans) or Resources on the same position than e; Humans > Resources
	 */
	public Element getFirstElementSamePos (Individual tmp) {
		Resource tmp_r = null;
		ArrayList<Element> tmp_elementsList = small_world.get(tmp.getPosition()).getElementsList();
		if (!tmp_elementsList.isEmpty ()) {
			for (Element tmp_e : tmp_elementsList) {
				if (tmp_e instanceof Individual && !areFriends ((Individual)tmp_e, tmp)) {
					return (Individual)tmp_e;
				} else if (tmp_r == null && tmp_e instanceof Resource) {
					tmp_r = (Resource) tmp_e;
				}
			}
		}
		return tmp_r; // either returns null, or the reference on the first Resource met in the list
	}
	
	/*
	 * Testing if two Individuals are in the same Tribe or not
	 */
	public boolean areFriends (Individual e1, Individual e2) {
		for(int i=0 ; i<tribe_list.size() ; i++) {
			if(tribe_list.get(i).getPopulation().contains(e1) && !tribe_list.get(i).getPopulation().contains(e2)
				|| tribe_list.get(i).getPopulation().contains(e2) && !tribe_list.get(i).getPopulation().contains(e1)) return false;
		}
		return true;
	}
	
	/*
	 * Search for the largest Tribe (#Individuals)
	 */
	private int maxSizeTribeList () {
		int tmp = 0;
		for (Tribe e : tribe_list) {
			if (e != null && !e.getPopulation().isEmpty()) tmp = Math.max (e.getPopulation().size(), tmp);
		}
		return tmp;
	}
	
	/*
	 * Computes the number of tribes, with at least 1 individual
	 */
	public int nbRemainingTribes () {
		int tmp = 0;
		for (Tribe e: tribe_list) {
			if (e != null && !e.getPopulation().isEmpty()) ++tmp;
		}
		return tmp;
	}
	
	/*
	 * Override for the run method of the thread, managing and lauching everything in the game
	 */
	@Override
	public synchronized void run () {
		
		Individual tmp;
		Position tmp_pos;
		boolean has_played;

		while (nbRemainingTribes () > 0) {
			if (gui.getPlay()) {
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
							}

							tmp_pos = getBestNextPosition (tmp);
							
							if (tmp_pos == null) System.err.println ("** New position is null");
							else if (tmp == null) System.err.println ("** Player to move is null");
							else move (tmp, tmp_pos);

							if (!has_played) {
								tmp.attack(getFirstElementSamePos (tmp));
								buryDeads (tmp.getPosition ());
							}
						}
					}
				}
				
				/*
				 * @author Belkacem @date 05/01/14
				 * Creating new individuals if there is enough "vital" resource
				 */
				for (Tribe t : tribe_list) {
					String vital_resource = null;
					if		(t.getIndividualType().equalsIgnoreCase("human"))		vital_resource = "Food";
					else if (t.getIndividualType().equalsIgnoreCase("bee"))			vital_resource = "Plutonium";
					else if (t.getIndividualType().equalsIgnoreCase("robot"))		vital_resource = "Metal";
					else System.err.println ("- Error, impossible to create " + t.getIndividualType() + "; it doesn't exist");
					
					if (vital_resource!= null && t.getResources().get(vital_resource) > 100) {
						Individual i = t.addIndividual ();
						i.setTribe (t);
						t.getResources().put (vital_resource, t.getResources().get(vital_resource)-100);
						gui.getMap().addIndividualGUI (new ElementGUI(i));
					}
				}
				
				gui.updateMapPanel();
			}
			
			/*
			 * Sleep instead of timer
			 * It may cause troubles and bugs...
			 */
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println ("\n\n\tEnd of Small World...");
		
		gui.disableButtons ();
		
		/*
		 * This can be used when the game is in "competition mode"
		 *		In this competition mode, individuals would try to live the longest time
		 *		The tribe which stays in life the longest time wins
		 * otherwise, there's just a "game over" written
		 */
		int tmp_num = 1;
		boolean done = false;
		for (Tribe t : tribe_list) {
			if (t.getPopulation().size() > 0) {
				gui.showWinners (t.getPopulation().get(0).getTypeName(), tmp_num);
			}
			++ tmp_num;
		}
		if (!done) gui.showGameOver();
	}
	
	/*
	 * Removes from the game the dead individuals (which life if < 0)
	 * and the dead resources (the resource is empty...)
	 */
	public synchronized void buryDeads (Position p) {
		small_world.get(p).buryDeads();
		
		for (Tribe t : tribe_list) {
			Iterator<Individual> it = t.getPopulation().iterator();
			while (it.hasNext()) {
				Individual x = it.next();
 				if (x.isDead ()) {
					it.remove();
					return ; // assuming there is not two identical Individuals
				}
			}
		}
	}

	/*
	 * Moving one individual from one position to another
	 * The parameter is already an intermediary position
	 */
	public synchronized void move (Individual tmp, Position new_pos) {
		if (!tmp.getPosition().equals(new_pos)) {
			try {
				gui.getMap().getCorrespondingElementGUI(tmp).setPrec_position(tmp.getPosition ());
				small_world.get(tmp.getPosition ()).remove (tmp);
				tmp.setPosition (new_pos);
				small_world.get(new_pos).add (tmp);
				/*
				 * @author Belkacem @date 06/01/14
				 * On each movement, the Indivudals now loses life (1 LP)
				 * It is more realistic
				 */
				tmp.setLife(tmp.getLife()-1);
			} catch (NullPointerException e) {
				System.err.println ("- Error, NullPointerException in SmallWorld.move()");
			}
		}
	}
	
	/*
	 * Adding a tribe with a type
	 * The type will be used to juste use the method addIndividual from tribe
	 * It makes sense to juste use addIndividual to create an individual
	 * Because an individual cannot exist without tribe (unless, he makes the tribe on its own)
	 */
	public Tribe addTribe (String type, Position base) {
		Tribe tmp = new Tribe (type, base);
		tribe_list.add (tmp);
		return tmp;
	}
	
	/*
	 * Classic toString override
	 */
	@Override
	public String				toString ()					{return "" + small_world;}
	
	/*
	 * Classic getters methods for SmallWorld class
	 */
	public SmallWorldGUI		getGui()					{return this.gui;}
	public Board				getBoard ()					{return small_world;}
	public Tribe				getTribeAt (int index)		{return tribe_list.get(index);}
	public ArrayList<Tribe>		getTribeList ()				{return tribe_list;}
	public ArrayList<Resource>	getResources()				{return resources;}
	
	/*
	 * Classic setters methods for SmallWorld Class
	 */
	public void					setGui (SmallWorldGUI gui)	{this.gui = gui;}
	public void					setBoard (Board b)			{small_world = b;}
	
	/*
	 * Methods to add things to lists, instead of classic getter, then add
	 * more encapsulation to the code
	 */
	public void					addResource (Resource r)	{resources.add (r);}
	/*
	 * @deprecated
	 * This method adds an individual to the tribe specified
	 * The new method to use is inside tribe, it directly adds the right type on individual to the tribe
	 */
	public void					addIndividual(Tribe t, Individual i)		{tribe_list.get(tribe_list.indexOf(t)).getPopulation().add(i);}
	public void					addIndividual(Individual i, int tribeIndex)	{tribe_list.get(tribeIndex).getPopulation().add(i);}
}
