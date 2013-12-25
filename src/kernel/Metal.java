package kernel;

public class Metal extends Resource {
	public Metal (Position p, String name) {
		super (p, name);
		life = 100;
	}
	
	public String getTypeName() {
		return "Metal";
	}
}
