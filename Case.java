
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
		String rep = "" + pos;
		if (elementsList != null && !elementsList.isEmpty ()) {
			for (Element e : elementsList) { // What if elementsList is empty?
				rep += "\n\t\t";
				rep += e;
			}
		} else rep += "\n\t\tno Element here";
		
		return rep;
	}

	public void add (Element e) {
		elementsList.add(e);
	}

	// Remove the first Element encountered in the elementsList which is the same as the one we want to delete
	public void remove (Element e) {
		// TODO We have to make sure that the removed element is the right one
		if (elementsList != null && !elementsList.isEmpty()) {
			for (Element x : elementsList) {
				if (x.equals (e)) {
					elementsList.remove(x); // TODO Check what is deletes: all the set of equal instances or only the one we asked for...
					return;
				}
			}
		}
	}
}
