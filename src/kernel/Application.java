package kernel;

import xml_parser.*;
import gui.*;

/**********************************************************************
 ***  Class containing the main method, and lauching the project	***
 ***  UTBM - LO43 - A2013											***
 ***  Lahouel Belkacem - Cadoret Luc - Le Morvan Valentin			***
 **********************************************************************
*/

public class Application {
	public static void main (String[] agrs) {
		
		SmallWorldParser SWP = new SmallWorldParser();
		SmallWorldGUI  swGUI = new SmallWorldGUI(SWP);
		
		System.out.println ("\n\t########################################\n");
	}
}
