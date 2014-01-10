package kernel.resources;

import kernel.Position;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 *	Class Rock inheriting from Resource
 *	This resource helps individuals (all of them) to improves themselves
 *  This helps the passage from one civilization to another
*/

public class Wood extends Resource {
	public Wood (Position p, String name) {
		super (p, name);
		life = 100;
	}
	
	@Override
	public String getTypeName() {
		return "Wood";
	}
}
