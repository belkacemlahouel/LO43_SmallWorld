
/*
 * This class inheritates from Element
 * A Human (e.g.) has certain capabilities that Resources do not have (e.g. attack or move)
*/

public class Human extends Individual {
	
	private static final int std_dmg = 20; // implementing a std_dmg attribute for generation of random damages
	private static final int std_pick = 5;
	private static final int max_life = 100; // to avoid over health, make the Human stop eating when it has enough health
	private static final int reach = 1;
	
	public Human (Position p, String name) {
		super (p, "human");
		life = max_life;
	}
	
	public String toString () {
		return "Human \"" + name + "\" at " + pos + " life: " + life;
	}

	/*
	 *	std_dmg +- random value
	 *	does random return a negative value eventually TODO
	 *	avoid extra-health (one Individual life < max_life)
	 *	The "dmg" on a Resource and on a Human is not the same (it depends on the type of the Individual)
	*/
	public void attack (Element e) {
		if (e != null && e.getPosition().equals(pos)) {
			if (e instanceof Human) {
				e.attack_received(std_dmg + Tools.rand (5, -5));
			} else if (e instanceof Resource && life < max_life) { // A Human with full life could pick up the Resource to make the others not take it... For the moment it's not possible because useless
				int tmp = Tools.rand (2, -2);
				life += Math.max(0, Math.min((life + tmp + std_pick), (max_life-life)));
				e.attack_received(std_pick + tmp);
			}
		}
	}

	public int getReach () {
		return reach;
	}
}
