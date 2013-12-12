
/*
 * Class containing the set of rules for the SmallWorld
 * There's a Board, and the methods on the board for the management of this "SmallWorld" application
*/

import java.util.ArrayList;

public class SmallWorld extends Thread {
	private Board small_world;
	private ArrayList<Element> team_1, team_2;
	
	public SmallWorld () {

		super ("Small World"); // Construction of the Thread

		small_world = new Board (3, 3);
		team_1 = new ArrayList<Element> (0);
		team_2 = new ArrayList<Element> (0);

		for (int i=0 ; i<3 ; ++i) { // These initializations should be done after parsing the XML file
			// Placing the elements of team_1 on the first line, the elements of team_2 on the last line + addin them to the Cases on the Board (small_world)
			team_1.add(new Element (small_world.get(0, i).getPosition(), "Element 1."+(i+1)));
				small_world.get(0, i).add(team_1.get(i));
			team_2.add(new Element (small_world.get(2, i).getPosition(), "Element 2."+(i+1)));
				small_world.get(2, i).add(team_2.get(i));
		}
		
		// Testing the move function: moving the Element (1.1) in (0, 0) to (0, 1) where we can already find 1.2 (and BTW testing the presence of multiple elements on one Case)
		move (team_1.get(0), small_world.get(0, 1).getPosition ()); // Move works, multiple elements on one Case works
		
		start (); // Why starting the Thread here?
	}
	
	public void run () {
		Element tmp;
		while (true) {
			for (int i=0 ; i<3 ; ++i) {
				tmp = team_1.get(i);
				move (tmp, small_world.randPosition ());
				// Attacking the people on this case, if they are from another team...
				tmp = team_2.get(i);
				move (tmp, small_world.randPosition ());
			}
			System.out.println ("" + this);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void move (Element e, Position new_pos) { // synchronized?
		if (!e.getPosition().equals(new_pos)) {
			small_world.get(e.getPosition ()).remove (e);
			e.setPosition (new_pos);
			small_world.get(new_pos).add (e);
		}
	}
	
	public String toString () {
		return "" + small_world;
	}
}
