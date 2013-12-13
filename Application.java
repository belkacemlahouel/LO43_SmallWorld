
/*
 *	Class containing the main method
 *	UTBM - LO43 - A2013
 *	Lahouel Belkacem
 *	Cadoret Luc - Le Morvan Valentin
 *	
*/

public class Application {
	public static void main (String[] agrs) {
		SmallWorld small_world = new SmallWorld ();
		System.out.println ("" + small_world);

		System.out.println ("\n\t########################################\n");

		small_world.run (); // The "while (true)" loop is inside the Thread named "Small World"
	}
}
