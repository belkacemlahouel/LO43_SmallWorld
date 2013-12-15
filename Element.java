import java.awt.Image;
import java.io.File;
import java.io.IOException;

/*
 *	An Element is something on the map
 *	It can either be an Individual or a Resource
 *	This is an abstract class
 *	It should be used later in the project, to generalize the treatments of all the Elements (whatever their type) which are on the map
 *	We also should seek for a way to not create a new Position for an Element, but maybe just keeping a reference...
 * 
 *	Implementing a WeakReference<Position>/<Case>?
*/

public class Element { // TODO go abstract
	protected Position pos;
	protected String name;
	protected int life ; // Resource: qty, Human: life
	
	public Element (Position pos_, String name_) {
		name = name_;
		pos = pos_; // Keeping in memory just the reference of the Position pos_
		
		/*try{
				imgElement = ImageIO.read(new File(name + ".png")); // The file of the element is referred as it's name + the .png file extension
	      } catch (IOException e) {
	        e.printStackTrace();
	    }  */
	}

	public Position getPosition () {
		return pos;
	}

	// We should remove the setter, because the user of this class should not access to the setter method for the Position
	// The Position should be either initialized when the Object is created or computed directly during the travel (e.g. for Individuals)
	public void setPosition (Position tmp_pos) {
		pos = tmp_pos;
	}
	
	public void setPosition (int x, int y) {
		pos.setX(x);
		pos.setY(y);
	}

	public void setLife (int l) {
		life = l;
	}
	
	public int getLife () {
		return life;
	}
	
	public boolean isDead () {
		return life <= 0;
	}
	
	public void attack_received (int dmg_) {
		life -= dmg_;
	}
	
	// TODO Override equals
}
