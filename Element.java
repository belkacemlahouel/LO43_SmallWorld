
/*
 *	An Element is something on the map
 *	It can either be an Individual or a Resource
 *	This is an abstract class
 *	It should be used later in the project, to generalize the treatments of all the Elements (whatever their type) which are on the map
 *	We also should seek for a way to not create a new Position for an Element, but maybe just keeping a reference...
 *	Implementing a WeakReference<Position>/<Case>?
 *
 *	TODO this class should go abstract, I'm using it this way just to test the comunication between elements through the Board
*/

public class Element {
	private Position pos;
	private String name;
	
	// How to add a doubled reference on the Board so I can implement the move method here?

	private int life;
	private static final int dmg = 10;

	public boolean isDead () {
		return life <= 0;
	}

	public int getLife () {
		return life;
	}

	public void attack_received (int dmg_) {
		life -= dmg_; // TODO Maybe I can add the removing of the Element here - Bad idea
	}

	public Position getPosition () {
		return pos;
	}

	// We should remove the setter, because the user of this class should not access to the setter method for the Position
	// The Position should be either initialized when the Object is created or computed directly during the travel (eg. for Individuals)
	public void setPosition (Position tmp_pos) {
		pos = tmp_pos;
	}

	public String toString () {
		return "Element " + name + " at " + pos + " life: " + life;
	}

	public Element (Position pos_, String name_) {
		name = name_;

		// Keeping in memory just the reference of the Position pos_
		pos = pos_; // Should try something to avoid unpleasant people who would like to modify it here TODO
		life = 100;
	}

	public Element (int x, int y, String name_) {
		name = name_;
		pos = new Position (x, y); // TODO no twice the same information! This i not the same "Position" as the one (with the same coordinates) in the Board...
	}

	public void attack (Element e) { // TODO For the moment the attack is constant
		if (e != null && e.getPosition().equals(pos)) {
			e.attack_received(dmg); // TODO we could incorporate a system of people protecting themselves
			// if (e.getLife () < 0) {
				//	TODO access to the Case at this Position on the Board to remove e if this Element is dead
			//	}
		} // if the Element e has no more life (life < 0) then, he will be removed from e
	}

	// TODO Override equals
}
