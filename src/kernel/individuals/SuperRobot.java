package kernel.individuals;

import kernel.Position;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 * The super stronger, and is cheated :p (but there is a smaller probability to fall on it)
 * It has almost everything two times more powerful
 */

public class SuperRobot extends Robot {
	
	public SuperRobot (Position p, String name) {
		super(p, name);
		super_indiv = true;
	}
	
	@Override
	public int getReach () {return super.getReach()*2;}
	
	@Override
	public int getMaxLife() {return super.getMaxLife()*2;}
	
	@Override
	public int getTotalDmg() {return super.getTotalDmg()*2;}
	
	@Override
	public int getTotalPick() {return super.getTotalPick()*2;}
	
	@Override
	public void nextCivilization () {civilization_std_bonus += (5*2);}
}
