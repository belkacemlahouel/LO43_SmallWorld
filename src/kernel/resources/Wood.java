package kernel.resources;

import kernel.Position;

public class Wood extends Resource {
	public Wood (Position p, String name) {
		super (p, name);
		life = 100;
	}
	
	@Override
	public String getTypeName() {
		return "Wood";
	}
}
