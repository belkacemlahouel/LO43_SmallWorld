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

	public Position getPosition () {
		return pos;
	}

	// We should remove the setter, because the user of this class should not access to the setter method for the Position
	// The Position should be either initialized when the Object is created or computed directly during the travel (e.g. for Individuals)
	public void setPosition (Position tmp_pos) {
		pos = tmp_pos;
	}

	public Element (Position pos_, String name_) {
		name = name_;

		// Keeping in memory just the reference of the Position pos_
		pos = pos_;
	}

	// TODO Override equals
}
