package kernel.individuals;

import kernel.Position;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 * The fighter is more interested in fights, and also less in picking up resources
 * It makes better damages
 * More efficient in fights
 */

public class FighterBee extends Bee {

	public FighterBee (Position p, String name) {
		super(p, name);
	}
	
	@Override
	public int getPriorityPick () {return (int) super.getPriorityPick()*2/3;}
	
	@Override
	public int getPriorityFight () {return super.getPriorityFight()*3/2;}
	
	@Override
	public int getTotalDmg () {return super.getTotalDmg()*2;}
}
