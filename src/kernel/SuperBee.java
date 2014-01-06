package kernel;

public class SuperBee extends Bee{
	
	public SuperBee(Position p, String name){
		super(p, name);
	}

	
	public int getStdDmg() {return 30;}

	public int getMaxLife() {return 130;}
}
