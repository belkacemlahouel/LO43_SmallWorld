package kernel;

public class Bee extends Individual{
	public Bee (Position p, String name) {
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
	public String getTypeName() {return "Bee";}
	
	@Override
	public int getVision () {return 10;}

	@Override
	public String getVitalResource () {return "Plutonium";}
}
