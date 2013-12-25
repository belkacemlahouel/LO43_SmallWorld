package kernel;

public class Rock extends Resource{
	public Rock (Position p, String name) {
		super (p, name);
		life = 100;
	}
	
	@Override
	public String getTypeName() {
		return "Rock";
	}
}
