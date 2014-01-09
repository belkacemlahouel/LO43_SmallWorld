package kernel.resources;

import kernel.Position;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 *	Class Food inheriting from Resource
 *	Food improves the health of humans
 * This is their vital resource as well
*/

public class Food extends Resource {
	
	public Food (Position pos, String name) {
		super (pos, name);
		life = 100;
	}
	
	@Override
	public String toString () {
		return "Food \"" + name + "\" at " + pos + " quantity: " + life;
	}

	@Override
	public String getTypeName() {
		return "Food";
	}
}