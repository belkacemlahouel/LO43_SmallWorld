package kernel;

public class Metal extends Resource {
	public Metal (Position p, String name) {
		super (p, name);
		life = 70;
	}
	
	public String getTypeName() {
		return "Metal";
	}
}
