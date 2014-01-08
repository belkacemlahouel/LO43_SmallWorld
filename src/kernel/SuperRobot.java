package kernel;

import kernel.individuals.Robot;

public class SuperRobot extends Robot {
	
	public SuperRobot(Position p, String name){
		super(p, name);
	}

	
	public int getStdDmg() {return 30;}

	public int getMaxLife() {return 80;}
}
