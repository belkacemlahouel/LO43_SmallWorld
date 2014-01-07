package kernel.resources;

import kernel.Position;

public class Plutonium extends Resource {
	public Plutonium (Position p, String name) {
		super (p, name);
		life = 50;
	}
	
	@Override
	public String getTypeName() {
		return "Plutonium";
	}
}
