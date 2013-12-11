
/*
 *	A Position is the set of coordinates (x, y) of an Element on the map (which is the Board)
*/

public class Position {
	private int x, y;

	public Position (int x_, int y_) {
		x = x_;
		y = y_;
	}

	public int getX () {
		return x;
	}

	public int getY () {
		return y;
	}

	public String toString () {
		return "x: " + x + " y: " + y;
	}
}
