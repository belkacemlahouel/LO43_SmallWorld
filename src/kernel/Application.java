package kernel;

import xml_parser.*;

/*
 *	Class containing the main method, and lauching the project
 *	UTBM - LO43 - A2013
 *	Lahouel Belkacem
 *	Cadoret Luc - Le Morvan Valentin
 *	
 * 
 * WHY IS TEAM 1 ALWAYS WINNING?
*/

public class Application {
	public static void main (String[] agrs) {
		
		SmallWorldParser SWP = new SmallWorldParser();
		SmallWorld small_world;
		small_world = SWP.createSW("data//Map.xml");
		
		System.out.println ("\n\t########################################\n");
		
	}
}
