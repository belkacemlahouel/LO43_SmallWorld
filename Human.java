
/*
 * This class inheritates from Element
 * A Human (e.g.) has certain capabilities that Resources do not have (e.g. attack or move)
*/

public class Human extends Individual {
	private static final int max_life = 100; // to avoid over health, make the Human stop eating when it has enough health
	
	public Human (Position p, String name) {
		super (p, "human");
		life = max_life;
	}
	
	public String toString () {
		return "Human \"" + name + "\" at " + pos + " life: " + life;
	}

}
