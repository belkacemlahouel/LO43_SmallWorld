package kernel.individuals;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import kernel.Case;
import kernel.Element;
import kernel.Position;
import kernel.resources.Resource;
import kernel.resources.Rock;
import kernel.SmallWorld;
import kernel.Tools;
import kernel.Tribe;
import kernel.resources.Wood;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 * Implementation of standards for any individual
 * This contains the priorities computation e.g.
 * and attacks etc
 * This class cannot be instancied
 * The priorities changes for Fighters and Pickers are parametrized
 * So, pickers/fighters just have to change these standard value
 */

public abstract class Individual extends Element {
	
	protected Position aim_position;
	protected Element target_element;
	protected int priority;
	protected Clip kick;
	protected int civilization_std_bonus = 0;
	protected boolean super_indiv = false;
	
	/*
	 * @author Belkacem @date 02/01/14
	 * Implementation of the resources sharing between individuals of the same tribe
	 */
	protected WeakReference<Tribe> tribe;
	
	/*
	 * @author Belkacem @date 02/01/14
	 * Checking if an Individual is this Individual's Friend
	 * New version, coming from the new "tribe" attribute
	 */
	public boolean isFriend (Individual e) {
		return tribe.get().getPopulation().contains(e);
	}
	
	public Individual (Position pos_, String name_) {
		super (pos_, name_);
		aim_position = null;
		life = getMaxLife ();
		
		String[] soundsList = {"kick1","kick2","kick3"};
		int rnd =(int)(Math.random() * (soundsList.length-1));
		
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("data//"+soundsList[rnd]+".wav").getAbsoluteFile());
	        kick = AudioSystem.getClip();
	        kick.open(audioInputStream);
	    } catch(Exception ex) {
	        System.err.println("- Error while playing sound.");
	        ex.printStackTrace();
	    }
	}

	public void attack (Element e) {
		/*
		 * @author Belkacem @date 05/01/14
		 * if the guy's life is too small (even critical), it will (at least try, depending on the Tribe's stock) heal from the Tribe stock
		 */
		if (life < getCriticalLife()) {
			int tmp_heal = Math.max(0, Math.min(tribe.get().getResources().get(getVitalResource()), getTotalPick()));
			tmp_heal *= vitalResourcePower();
			life += tmp_heal; // Don't need to test this guy's interval, its life is critical
			tribe.get().getResources().put (getVitalResource(), tribe.get().getResources().get(getVitalResource())-tmp_heal);
		}
		
		if (e != null && e.getPosition().equals(pos)) {
			if (e instanceof Individual) {
				try {
					kick.start();
				} catch(Exception ex) {
			        System.err.println("- Error while playing sound.");
			        ex.printStackTrace();
			    }
				
				e.attack_received(getTotalDmg ());
			} else if (e instanceof Resource) { // && life < getMaxLife ()) {
				/*
				 * @author Belkacem @date 05/01/14
				 * Implementation of the Resources sharing inside each tribe
				 * If there is more than 50 in Wood and in Rock, the tribe moves to the next civilization
				 * This civilization offers them a bonus in attack
				 */
				int tmp = Math.max (0, Math.min(getTotalPick (), e.getLife())); // We test the interval of life for this Resource's stock
				
				if (e.getTypeName().equalsIgnoreCase(getVitalResource())) {
					tmp *= vitalResourcePower();
					life = Math.max(0, Math.min((life + tmp), getMaxLife ())); // we test the interval of life for this guy
					tmp /= vitalResourcePower();
					
					/*
					 * @author Belkacem @date 05/01/14
					 * When a guy picks some VitalResource, now he will pick the same amount for the tribe (or less, if there is not anymore left)
					 * Or more (in the case where he does not need everything)
					 */
					int tmp_for_tribe = Math.max (0, Math.min (e.getLife()-tmp, getTotalPick())); // We assume we already took this resource in tmp qty
					tribe.get().getResources().put (getVitalResource(), tribe.get().getResources().get(getVitalResource())+tmp_for_tribe);
					tmp += tmp_for_tribe;
				} else {
					tribe.get().getResources().put(e.getTypeName(), tribe.get().getResources().get(e.getTypeName())+tmp);
					
					if (tribe.get().getResources().get("Wood") > 50 && tribe.get().getResources().get("Rock") > 50) {
						for (Individual x : tribe.get().getPopulation()) {
							x.nextCivilization(); // System.out.println ("Next civilization");
						}
						
						tribe.get().getResources().put("Wood", tribe.get().getResources().get("Wood")-50);
						tribe.get().getResources().put("Rock", tribe.get().getResources().get("Rock")-50);
					}
				}
				e.attack_received(tmp);
			}
		}
	}
	
	/*
	 * Use of sw to get the list of accessible Positions, in function of the vision
	 * Selection of the best Position to go
	 * If no accessible Position available, we need to find a random Position
	 * Check if they are friends: send tribeList?
	 * I will have to compute the priority for each element: should I prefer closest elements?
	 * weakest ennemy (Individual): less life
	 */
	public Position newAim (SmallWorld sw) {
		Position back_up = aim_position;
		
		aim_position = null;
		priority = 0;
		
		ArrayList<Case> case_list = sw.getBoard().getAround (this);
		ArrayList<Element> elements_list = new ArrayList<Element> (0);
		int tmp_prio = 0;
				
		for (Case c : case_list) {
			elements_list.addAll (c.getElementsList());
		}
		
		for (Element e : elements_list) {
			tmp_prio = computePriority(sw, e);
			if (tmp_prio > priority) {
				priority = tmp_prio;
				target_element = e;
				aim_position = e.getPosition ();
			}
		}
		
		if (aim_position == null || priority == 0) {
			if (back_up == null) aim_position = sw.getBoard().randPosition();
			else if (Tools.distance (getPosition(), back_up) <= 5) aim_position = sw.getBoard().randPosition();
			else aim_position = back_up;
		}
		
		return aim_position;
	}
	
	public int computePriority (SmallWorld sw, Element e) {
		int rep = 0;
		
		if (this instanceof Individual) {
			/*
			 * Computation of the number of ennemies and friends
			 * Computation of the maximal std dmg that this element can receive
			 * Computation of the maximal std dmg that i could receive
			 * tot_received and tot_given are the sum of dmg received/given at the position of e
			 * We do not count our supposed dmg inside tot_given
			 */
			int nb_ennemies = 0, nb_friends = 0, nb_resources = 0;
			int tot_received = 0, tot_given = 0;
						
			ArrayList<Element> tmp = sw.getBoard().get(e.getPosition()).getElementsList();
			for (Element g : tmp) {
				if (g instanceof Individual) {
					if (sw.areFriends (this, (Individual) g)) {
						++ nb_friends;
						tot_given += ((Individual)g).getStdDmg();
					} else {
						++ nb_ennemies;
						tot_received += ((Individual)g).getStdDmg();
					}
				} else nb_resources += g.getLife ();
			}
			
			if (nb_resources > 0 || nb_ennemies > 0) {
				
				/*
				 * computatation of the distance part
				 * this follows a mathematical inverse function
				 * f : e |-> cst / distance (me, e)
				 * beware with the division with 0
				 * this cst may change following the type of Individual
				 */
				if (Tools.distance (getPosition(), e.getPosition()) != 0) {
					rep += 20/Tools.distance (getPosition(), e.getPosition());
				} else if (nb_ennemies > 0 || nb_resources > 0) rep += 30;

				/*
				 * computation of the Individual part
				 * two cases: e is a Resource or an Individual
				 * two cases: e is an ennemy or not
				 * we give an importance to the type of the Individual
				 * we take care of a possible ennemy in the 
				 * If this is an ennemy, we check its remaining life and strength
				 * 
				 * Higher priority for places where there is more friends than ennemies...
				 * We multiply the final priority with a ratio
				 */
				if (e instanceof Individual) {
					if (sw.areFriends ((Individual)e, this)) {
						/*
						 * We should compute the maximal damages we could get from targeting this Element e
						 * Because of supposed several ennemies at the same Position
						 * f : dmg |-> 10/(remaining_life - dmg)
						 * with dmg being the sum of all the dmg that this ennemy could receive
						 * 
						 * The more there is friends on that position, the more the Individual will try to go there
						 * We need to check if there is also ennemies on this position
						 */
						if (tot_given > tot_received && nb_ennemies > 0) {
							rep += nb_friends + (tot_given-tot_received);
						}
					} else {
						/*
						 * We can manage higher priorities if this is a friend in help
						 * with ennemies on the same Position
						 * 
						 * If there is a lot of ennemies and we can kill them easily, we try to go there
						 * There is a higher priority
						 */
						if (tot_given > tot_received) {
							rep += getPriorityFight()*(nb_ennemies + (tot_given-tot_received)); // *20
						}
					}
				} else if (e instanceof Resource) {
					/*
					 * for the Food, we take care of the remaining life of the current Individual
					 * this follows an inverse mathematical function
					 * f : life |-> cst / (life - critical_life)
					 * critical_life is a cst as well, and again it may change following the type of Individual
					 */
					
					/*
					 * @author Belkacem @date 05/01/14
					 * Same importance for the Vital Resource (in function of the type of the individual)
					 * The more we get closer to a new Civilization age, the more the priority for wood and rock is big
					 * A new Civilization age comes every 50 (of Wood and Rock)
					 */
					if (e.getTypeName().equalsIgnoreCase(getVitalResource())) {
						if (getLife() <= getCriticalLife()) {
							rep += getPriorityPick()+10; // 30;
						} else {
							rep += getPriorityPick()/(getLife()-getCriticalLife()); // 20/...
						}
					} else if (e instanceof Wood || e instanceof Rock) {
						
						if (tribe.get() == null) System.err.println ("- Error, Tribe for this Individual is null");
						
						HashMap<String, Integer> xyz = tribe.get().getResources();
						
						if (100 > tribe.get().getResources().get(e.getTypeName())) {
							rep += getPriorityPick()/(2*(100-tribe.get().getResources().get(e.getTypeName()))); // 10/(100-tribe.get().getResources().get(e.getTypeName()));
						} else {
							rep += getPriorityPick(); // 20;
						}
					}
				}
			}
		}
		return rep;
		
		/*
		 * Maybe sometimes, they prefer to go on the same Position as an ennemy
		 *		and they choose to pick up Resources > kill Individual
		 */
	}
	
	/*
	 * getters and setters for Individual class
	 * classic override on toString method
	 */
	public Position			getAimPosition ()				{return aim_position;}
	public int				getCriticalLife ()				{return (int) getMaxLife()/5;}
	public Element			getTargetElement ()				{return target_element;}
	public void				setTribe (Tribe t)				{tribe = new WeakReference<Tribe> (t);}
	public void				setPosition	(Position tmp_pos)	{pos = tmp_pos;}

	/*
	 * @author Belkacem @date 07/01/14
	 * Implementing pickers and fighters, and priorities for fighting and picking
	 */
	public int				getPriorityFight ()				{return 20;}
	public int				getPriorityPick ()				{return 20;}
	public boolean			isSuper ()						{return super_indiv;}
	
	@Override
	public String toString () {return getTypeName () + "\"" + name + "\" at " + pos + " life: " + life;}
	
	/*
	 * abstract methods for Individual class, making a template
	 * I choosed to use these methods, and this pattern, because wa cannot have final and static attributes here,
	 *		And cthen change them when inheriting
	 * replacing final and static attributes inside each kind of Individual (Human, Robot, etc)
	 */
	public abstract int		getReach ();
	public abstract int		getMaxLife ();		// to make the human avoid overhealth, we make him stop when its life is full
	public abstract int		getStdDmg ();		// generation of random damages, around one std value
	public abstract int		getTotalDmg ();
	public abstract int		getStdPick ();		// generation of random damages on resources: it's the quantity picked up each time
	public abstract int		getTotalPick ();
	public abstract int		getVision ();
	public abstract String	getVitalResource ();
	public abstract int		vitalResourcePower();
	public abstract void	nextCivilization ();
}
