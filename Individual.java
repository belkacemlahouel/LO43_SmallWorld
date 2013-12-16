public class Individual extends Element{
	private final int reach = 1;
	private final int std_dmg = 20; // implementing a std_dmg attribute for generation of random damages
	private final int std_pick = 5;
	private final String raceName;
	// TODO : add a "goalPosition"
	
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
			}/* else if (e instanceof Resource && life < max_life) { // A Human with full life could pick up the Resource to make the others not take it... For the moment it's not possible because useless
				int tmp = Tools.rand (2, -2);
				life += Math.max(0, Math.min((life + tmp + std_pick), (max_life-life)));
				e.attack_received(std_pick + tmp);
			}*/
		}
	}
	
	public int getReach () {
		return reach;
	}
	
	public Individual(Position pos_, String name_,String raceName_) {
		super(pos_, name_);
		raceName = raceName_;
		
	}	
	
	public String getRaceName() {
		return raceName;
	}
	
}
