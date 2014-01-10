package kernel;
/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 *	A Position is the set of coordinates (x, y) of an Element on the map (which is the Board)
*/

public class Position {
	private int x, y;

	public Position (int x_, int y_) {
		x = x_;
		y = y_;
	}

	/*
	 * Classic toString override
	*/
	@Override
	public String toString () {
		return "x: " + x + " y: " + y;
	}
	
	/*
	 * Classic equals method
	 * Comparing if two points are the same
	 * This makes a strong verification (not just the reference)
	*/
	public boolean equals (Position pos) {
		return pos.x == x && pos.y == y;
	}

	/*
	 * Classic setters methods
	 * One method for each coordinate
	*/
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	
	/*
	 * Classic getters methods
	 * One getter for each coordinate
	*/
	public int getX () {return x;}
	public int getY () {return y;}
}
