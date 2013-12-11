
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

	public Position getPosition () {
		return pos;
	}

	// We should remove the setter, because the user of this class should not access to the setter method for the Position
	// The Position should be either initialized when the Object is created or computed directly during the travel (eg. for Individuals)
	public void setPosition (Position tmp_pos) {
		pos = tmp_pos;
	}

	public String toString () {
		return "element";
	}

	public Element (int x, int y) {
		pos = new Position (x, y); // TODO no twice the same information! This i not the same "Position" as the one (with the same coordinates) in the Board...
	}
}
