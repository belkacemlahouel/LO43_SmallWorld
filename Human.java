
/*
 * This class inheritates from Element
 * A Human has certain capabilities that Resources do not have
*/

public class Human extends Element {
	
	private static final int std_dmg = 20; // implementing a std_dmg attribute for generation of random damages
	private static final int std_pick = 5;
	private static final int max_life = 100; // to avoid over health, make the Human stop eating when it has enough health
	private Position oldPos;
	
	public Human (Position p, String name) {
		super (p, name);
		life = max_life;
	}
	
	public String toString () {
		return "Human \"" + name + "\" at " + pos + " life: " + life;
	}
	
	public void attack (Element e) {
		if (e != null && e.getPosition().equals(pos)) {
			if (e instanceof Human) {
				e.attack_received(std_dmg + Tools.rand (5, -5)); // Random value +- std_dmg, does random returns a negative value?
			} else if (e instanceof Resource && life < max_life) { // Assuming the guy does not want to pick up a Resource, if it is useless for him (already life == max_life)
				int tmp = Tools.rand (2, -2);
				life += Math.max(0, Math.min((life + tmp + std_pick), (max_life-life))); // Normally it's to avoid extra-health
				e.attack_received(std_pick + tmp); // Not the same dmg for a Resource as for a Human
			}
		}
	}

	public Position getOldPos() {
		return oldPos;
	}

	public void setOldPos(Position oldPos) {
		this.oldPos = oldPos;
	}
	
}
