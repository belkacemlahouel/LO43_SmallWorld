package kernel.resources;

import kernel.Position;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 *	Class Metal inheriting from Resource
 *	Food improves the health of robots
 * This is their vital resource as well
*/
public class Metal extends Resource {
	public Metal (Position p, String name) {
		super (p, name);
		life = 70;
	}
	
	public String getTypeName() {
		return "Metal";
	}
}
