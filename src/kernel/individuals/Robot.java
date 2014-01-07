package kernel.individuals;

import kernel.Position;
import kernel.Tools;

public class Robot extends Individual {
	
	public Robot (Position p, String name) {
		super (p, name);
	}

	@Override
	public int getReach() {return 3;}

	@Override
	public int getMaxLife() {return 50 + civilization_std_bonus*2;}

	@Override
	public int getStdDmg() {return 15;}

	@Override
	public int getTotalDmg() {return getStdDmg () + civilization_std_bonus + Tools.rand(2, 2);}

	@Override
	public int getStdPick() {return 10;}

	@Override
	public int getTotalPick() {return getStdPick () + civilization_std_bonus + Tools.rand(1, 1);}

	@Override
	public String getTypeName() {return "Robot";}
	
	@Override
	public int getVision () {return 10;}
	
	@Override
	public String getVitalResource () {return "Metal";}
	
	@Override
	public int vitalResourcePower () {return 1;}
	
	@Override
	public void nextCivilization () {System.out.println ("NEXT CIVILIZATION : ROBOT"); civilization_std_bonus += 4;}
}
