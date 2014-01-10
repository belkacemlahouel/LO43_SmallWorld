package kernel.resources;

import kernel.Element;
import kernel.Position;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 * This class implements the Resources, inheriting from Element
 * Some methods are still the same as in Element, but one should understand them differently
 * e.g. life should be understood as the remaining quantity of the resource
*/

public abstract class Resource extends Element {
	
	public Resource (Position pos, String name) {
		super (pos, name);
	}
	
}
