package kernel.individuals;

import kernel.Position;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 * The picker is more interested in resources, and also less in fighting
 * More efficient for picking up resources
 */

public class PickerHuman extends Human {

	public PickerHuman (Position p, String name) {
		super(p, name);
	}
	
	@Override
	public int getPriorityPick () {return (int) super.getPriorityPick()*3/2;}
	
	@Override
	public int getPriorityFight () {return super.getPriorityFight()*2/3;}
	
	@Override
	public int getTotalPick () {return super.getTotalPick()*2;}
}
