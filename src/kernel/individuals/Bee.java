package kernel.individuals;

import kernel.Position;
import kernel.Tools;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 * Implementation of a regular ("normal") bee
 */

public class Bee extends Individual {
	
	public Bee (Position p, String name) {
		super (p, name);
	}

	@Override
	public int getReach () {return 3;}
	
	@Override
	public int getMaxLife() {return 80 + civilization_std_bonus*2;}
	
	@Override
	public int getStdDmg() {return 10;}
	
	@Override
	public int getTotalDmg() {return getStdDmg () + civilization_std_bonus + Tools.rand(5, -5);}

	@Override
	public int getStdPick() {return 5;}
	
	@Override
	public int getTotalPick() {return getStdPick() + civilization_std_bonus + Tools.rand(2, -2);}
	
	@Override
	public String getTypeName() {return "Bee";}
	
	@Override
	public int getVision () {return 15;}

	@Override
	public String getVitalResource () {return "Plutonium";}
	
	@Override
	public int vitalResourcePower () {return 2;}
	
	@Override
	public void nextCivilization () {civilization_std_bonus += 2;}
}
