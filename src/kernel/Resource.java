package kernel;

import kernel.*;
import xml_parser.*;

/*
 * This class implements the Resources, inheriting from Element
 * Some methods are still the same as in Element, but one should understand them differently
 * 
*/

public class Resource extends Element {
	public Resource (Position pos, String name) {
		super (pos, name);
		life = 10;
	}
	
	public String toString () {
		return "Resource \"" + name + "\" at " + pos + " life: " + life;
	}

}
