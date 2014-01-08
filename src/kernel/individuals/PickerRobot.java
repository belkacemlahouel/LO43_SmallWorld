package kernel.individuals;

import kernel.Position;

public class PickerRobot extends Robot {

	public PickerRobot (Position p, String name) {
		super(p, name);
	}
	
	@Override
	public int getPriorityPick () {return super.getPriorityFight()*3/2;}
	
	@Override
	public int getTotalPick () {return super.getTotalPick()*2;}
}
