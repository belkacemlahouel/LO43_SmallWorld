package kernel.individuals;

import kernel.Position;

public class PickerBee extends Bee {

	public PickerBee (Position p, String name) {
		super(p, name);
	}
	
	@Override
	public int getPriorityPick () {return (int) super.getPriorityPick()*3/2;}
	
	@Override
	public int getPriorityFight () {return super.getPriorityFight()*2/3;}
	
	@Override
	public int getTotalPick () {return super.getTotalPick()*2;}
}
