package kernel;

public class Plutonium extends Resource {
	public Plutonium (Position p, String name) {
		super (p, name);
		life = 100;
	}
	
	@Override
	public String getResourceTypeName() {
		// TODO Auto-generated method stub
		return "plutonium";
	}
}
