
/*
 *	Class containing the main method
 *	UTBM - LO43 - A2013
 *	Lahouel Belkacem
 *	Cadoret Luc - Le Morvan Valentin
 *	
*/

public class SmallWorld {
	public static void main (String[] args) {
		System.out.println ("\tHello World!\n");

		Board board = new Board (3, 3);
		System.out.println ("Board printing:\n" + board.toString());
		
		System.out.println ("\n\tBye World!");
	}
}
