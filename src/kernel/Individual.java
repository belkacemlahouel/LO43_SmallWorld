package kernel;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * TODO define rules and prioriies in function of the type of the Individual
 * @author Belkacem Lahouel
 */

public abstract class Individual extends Element {
	
	protected Position aim_position; // The Individual try to go to this Position, and its aimPosition does not change until then
	
	public Individual (Position pos_, String name_) {
		super(pos_, name_);
		aim_position = null;
		life = getMaxLife (); // WTF?
	}
	
	/**
	 *	total_dmg = std_dmg +- random value
	 *	does random return a negative value eventually TODO
	 *	avoid extra-health (one Individual life < max_life)
	 *	The "dmg" on a Resource and on a Human is not the same (it depends on the type of the Individual)
	 *	A Human with full life could pick up the Resource to make the others not take it... For the moment it's not possible because useless
	*/
	public void attack (Element e) {
	
		
		if (e != null && e.getPosition().equals(pos)) {
			if (e instanceof Individual) {
				try {
			        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("data//kick.wav").getAbsoluteFile());
			        Clip clip = AudioSystem.getClip();
			        clip.open(audioInputStream);
			        clip.start();
			    } catch(Exception ex) {
			        System.out.println("Error while playing sound.");
			        ex.printStackTrace();
			    }
				e.attack_received(getTotalDmg ());
			} else if (e instanceof Resource && life < getMaxLife ()) {
				int tmp = getTotalPick ();
				life = Math.max(0, Math.min((life + tmp), getMaxLife ())); // TODO
				e.attack_received(tmp);
			}
		}
	}
	
	public Position newAim (SmallWorld sw) {
		Position rep = null;
		
		// Use of sw to get the list of accessible Positions, in function of the vision
			// Selection of the best Position to go
			// If no accessible Position available, we need to find a random Position
		
		// Check if they are friends: send tribeList?
		// I will have to compute the priority for each element: should I prefer closest elements?
		// weakest ennemy (Individual): less life
		
		ArrayList<Case> case_list = sw.getBoard().getAround (this);
		Element tmp_r = null, tmp_e = null;
		
		if (case_list == null || case_list.isEmpty()) System.out.println ("EMPTY");
		
		for (Case c : case_list) {
			for (Element e : c.getElementsList()) {
				if (tmp_r == null && e instanceof Resource) { // first Resource found
					tmp_r = e;
				} else if (tmp_e == null && e instanceof Individual &&
							!sw.areFriends ((Individual)e, this)) { // first ennemy Individual found
					tmp_e = e;
				}
			}
		}
		
		if (tmp_e == null && tmp_r == null) {
			rep = sw.getBoard().randPosition();
		} else {
			if (tmp_e != null) rep = tmp_e.getPosition(); // tmp_e & tmp_e.getPosition are connected???
			else rep = tmp_r.getPosition();
		}
		
		aim_position = rep;
		
		return rep;
	}
	
	public Position getAimPosition () {return aim_position;}
	// public void setAimPosition (Position p) {aim_position = p;}
	public void setPosition (Position tmp_pos) {pos = tmp_pos;} // only when the Element is created or set during a move (Individuals)
	
	@Override
	public String toString () {
		return getRaceName () + "\"" + name + "\" at " + pos + " life: " + life;
	}
	
	public abstract int getReach ();
	public abstract int getMaxLife (); // to make the human avoid overhealth, we make him stop when its life is full
	public abstract int getStdDmg (); // generation of random damages, around one std value
	public abstract int getTotalDmg ();
	public abstract int getStdPick (); // generation of random damages on resources: it's the quantity picked up each time
	public abstract int getTotalPick ();
	public abstract String getRaceName ();
	public abstract int getVision ();
}
