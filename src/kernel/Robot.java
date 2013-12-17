package kernel;

import kernel.*;
import xml_parser.*;

public class Robot extends Individual {
	private static final int max_life = 200;
	
	public Robot(Position pos_,String name_)
	{
		super(pos_,name_,"robot");
		life = max_life;
	}

	public static int getMaxLife() {
		return max_life;
	}
}
