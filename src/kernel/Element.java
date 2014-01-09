package kernel;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 *	An Element is something on the map
 *	It can either be an Individual or a Resource
 *	This is an abstract class
 *	It should be used later in the project, to generalize the treatments of all the Elements (whatever their type) which are on the map
 *	We also should seek for a way to not create a new Position for an Element, but maybe just keeping a reference...
 * 
 *	Implementing a WeakReference<Position>/<Case>?
*/

public abstract class Element {
	protected Position pos;
	protected String name;
	protected int life ; // Resource: qty, Human: life
	
	public Element (Position pos_, String name_) {
		name = name_;
		pos = pos_; // just the reference
	}

	public Position getPosition () {
		return pos;
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
	
	public String getName () {
		return name;
	}
	
	public abstract String getTypeName ();
}
