package kernel.individuals;

import kernel.Position;

public class FighterBee extends Bee {

	public FighterBee (Position p, String name) {
		super(p, name);
	}
	
	@Override
	public int getPriorityFight () {return super.getPriorityFight()*3/2;}
	
	@Override
	public int getTotalDmg () {return super.getTotalDmg()*2;}
}
