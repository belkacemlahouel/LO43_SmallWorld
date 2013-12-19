package kernel;

public class Robot extends Individual {
	
	public Robot (Position p, String name) {
		super (p, name);
	}

	@Override
	public int getReach() {return 3;}

	@Override
	public int getMaxLife() {return 50;}

	@Override
	public int getStdDmg() {return 15;}

	@Override
	public int getTotalDmg() {return getStdDmg () + Tools.rand(2, 2);}

	@Override
	public int getStdPick() {return 10;}

	@Override
	public int getTotalPick() {return getStdPick () + Tools.rand(1, 1);}

	@Override
	public String getRaceName() {return "Robot";}
}
