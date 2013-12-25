package kernel;

/*
 *	Class Food inheriting from Resource
 *	Food improves the health of Individuals
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
	public String getResourceTypeName() {
		// TODO Auto-generated method stub
		return "food";
	}
}