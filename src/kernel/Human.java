package kernel;

/*
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
	public int getMaxLife() {return 100;}
	
	@Override
	public int getStdDmg() {return 20;}
	
	@Override
	public int getTotalDmg() {return getStdDmg () + Tools.rand(5, -5);}

	@Override
	public int getStdPick() {return 5;}
	
	@Override
	public int getTotalPick() {return getStdPick() + Tools.rand(2, -2);}
	
	@Override
	public String getTypeName() {return "Human";}
	
	@Override
	public int getVision () {return 13;}
}
