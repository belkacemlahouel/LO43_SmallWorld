
public class Robot extends Individual {
	private static final int max_life = 200;
	
	public Robot(Position pos_)
	{
		super(pos_,"robot");
		life = max_life;
	}
}
