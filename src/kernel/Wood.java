package kernel;

public class Wood extends Resource{
	public Wood (Position p, String name) {
		super (p, name);
		life = 100;
	}
	
	@Override
	public String getResourceTypeName() {
		// TODO Auto-generated method stub
		return "wood";
	}
}
