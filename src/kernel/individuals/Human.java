package kernel.individuals;

import kernel.Position;
import kernel.Tools;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 * Implementation of a regular ("normal") human
 * This class inheritates from Element
 * A Human (e.g.) has certain capabilities that Resources do not have (e.g. attack or move)
*/

public class Human extends Individual {
	
	public Human (Position p, String name) {
		super (p, name);
	}

	@Override
	public int getReach () {return 1;}
	
	@Override
	public int getMaxLife() {return 100 + civilization_std_bonus*2;}
	
	@Override
	public int getStdDmg() {return 20;}
	
	@Override
	public int getTotalDmg() {return getStdDmg () + civilization_std_bonus + Tools.rand(5, -5);}

	@Override
	public int getStdPick() {return 5;}
	
	@Override
	public int getTotalPick() {return getStdPick() + civilization_std_bonus + Tools.rand(2, -2);}
	
	@Override
	public String getTypeName() {return "Human";}
	
	@Override
	public int getVision () {return 13;}
	
	@Override
	public String getVitalResource () {return "Food";}
	
	@Override
	public int vitalResourcePower () {return 1;}
	
	@Override
	public void nextCivilization () {civilization_std_bonus += 3;}
}
