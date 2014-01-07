package kernel.individuals;

import kernel.Position;

public class SuperRobot extends Robot {
	
	public SuperRobot (Position p, String name) {
		super(p, name);
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
