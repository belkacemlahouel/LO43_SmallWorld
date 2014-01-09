package kernel.resources;

import kernel.Position;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 *	Class Plutonium inheriting from Resource
 *	Food improves the health of Bees
 * This is their vital resource as well
*/

public class Plutonium extends Resource {
	public Plutonium (Position p, String name) {
		super (p, name);
		life = 50;
	}
	
	@Override
	public String getTypeName() {
		return "Plutonium";
	}
}
