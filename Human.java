
/*
 * This class inheritates from Element
 * A Human has certain capabilities that Resources do not have
*/

public class Human extends Element {
	
	private static final int dmg = 20; // implementing a std_dmg attribute for generation of random damages
	private int life = 100; // initialization at declaration
	// private static final int max_life = 100; // to avoid over health, make the Human stop eating when it has enough health
	
	public int getLife () {
		return life;
	}
	
	public Human (Position p, String name) {
		super (p, name);
	}

	public void attack_received (int dmg_) {
		life -= dmg_;
	}
	
	public String toString () {
		return "Human " + name + " at " + pos + " life: " + life;
	}
	
	public void attack (Human e) {
		if (e != null && e.getPosition().equals(pos)) {
			e.attack_received(dmg);
		}
	}
	
	public boolean isDead () {
		return life <= 0;
	}
}
