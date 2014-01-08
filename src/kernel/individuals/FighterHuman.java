package kernel.individuals;

import kernel.Position;

public class FighterHuman extends Human {

	public FighterHuman (Position p, String name) {
		super(p, name);
	}
	
	@Override
	public int getPriorityFight () {return super.getPriorityFight()*3/2;}
	
	@Override
	public int getTotalDmg () {return super.getTotalDmg()*2;}
}
