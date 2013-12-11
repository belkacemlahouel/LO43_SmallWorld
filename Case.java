
/*
 *	Class managing the Cases
 *	A Case has one Position and is stored in the Board
 *		Recall: the Board should be implementing a singleton pattern
*/

import java.util.ArrayList;
import java.util.List;

public class Case {
	private List<Element> elementsList;
	private Position pos;

	public Case (Position p) {
		pos = p;
		elementsList = new ArrayList<Element> ();
	}

	public Case (int x_, int y_) {
		Position pos_ = new Position (x_, y_); // how to "get" a Position from the ones already created?
		new Case (pos_);
	}

	public Position getPosition () {
		return pos;
	}

	public String toString () {
		String rep = pos.toString();
		if (!elementsList.isEmpty ()) {
			for (Element e : elementsList) { // What if elementsList is empty?
				rep += "\n\t";
				rep += e;
			}
		} else rep += "\n\tno Element here";
		
		return rep;
	}

	public void add (Element e) {
		elementsList.add(e);
	}

	public void remove (Element e) {
		// TODO We have to make sure that the removed element is the right one
	}
}
