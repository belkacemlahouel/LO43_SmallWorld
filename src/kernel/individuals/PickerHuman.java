package kernel.individuals;

import kernel.Position;

public class PickerHuman extends Human {

	public PickerHuman (Position p, String name) {
		super(p, name);
	}
	
	@Override
	public int getPriorityFight () {return super.getPriorityFight()*3/2;}
}
