package kernel.individuals;

import kernel.Position;

public class PickerBee extends Bee {

	public PickerBee (Position p, String name) {
		super(p, name);
	}
	
	@Override
	public int getPriorityFight () {return super.getPriorityFight()*3/2;}
}
