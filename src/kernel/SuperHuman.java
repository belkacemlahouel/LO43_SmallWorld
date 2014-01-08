package kernel;

import kernel.individuals.Human;

public class SuperHuman extends Human {

		public SuperHuman(Position p, String name){
			super(p, name);
		}
	
		
		public int getStdDmg() {return 40;}

		public int getMaxLife() {return 150;}
}
