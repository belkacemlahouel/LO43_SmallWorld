package kernel.individuals;

import kernel.Position;

public class FighterRobot extends Robot {

	public FighterRobot (Position p, String name) {
		super(p, name);
	}
	
	@Override
	public int getPriorityFight () {return super.getPriorityFight()*3/2;}
}
