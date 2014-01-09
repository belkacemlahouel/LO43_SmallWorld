package kernel;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 * Implementation of static methods used as tools during the project
 * For example, the generation of a random number between two others
*/

public class Tools {
	/*
	 *	Random value between lower and higher as [lower, higher]
	 *  It is inverted because the operation made (which is higher - lower)...
	*/
	public static int rand (int higher, int lower) {
		return (int) ((Math.random() * (higher - lower)) + lower);
	}

	/*
	 *	Computes the distance between two Positions given in parameters
	 *	The result is given in the number of Cases through which the guy has to go
	 *	The departure Position (associated to a Case in the SmallWorld) is not counted, because the Human is already on it
	 *	The ending Position is counted (the Human has to go on it)
	 *	From p1 to p2... (but actually it does not matter)
	*/
	public static int distance (Position p1, Position p2) {
		return Math.abs (p1.getX() - p2.getX()) + // not counting the departure Position (p1)
				Math.abs (p1.getY() - p2.getY()); // not counting the first Position, going only
				// Normally, we should have the right distance, just not counting the departure Position in the distance
	}
}
