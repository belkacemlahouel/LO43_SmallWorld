
/*
 *	Class managing the Cases
 *	A Case has one Position and is stored in the Board
 *		Recall: the Board should be implementing a singleton pattern
*/

import java.util.ArrayList;
import java.util.Iterator;

public class Case {
	private ArrayList<Element> elementsList;
	private Position pos;
	
	public ArrayList<Element> getElementsList () {
		return elementsList;
	}

	public Case (Position p) {
		pos = p;
		elementsList = new ArrayList<Element> ();
	}

	public Case (int x_, int y_) {
		Position pos_ = new Position (x_, y_);
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
	
	public synchronized void buryDeads () {
		Iterator<Element> it = elementsList.iterator();
		while (it.hasNext()) {
			Element x = it.next();
			if (x.isDead()) {
				it.remove();
				return ; // assuming there's only one instance Element, all differents...
			}
		}
	}

	// Remove the first Element encountered in the elementsList which is the same as the one we want to delete
	public synchronized void remove (Element e) {
		// TODO We have to make sure that the removed element is the right one
		if (elementsList != null && !elementsList.isEmpty()) {
			// for (Element x : elementsList) { // We cannot delete while we go through the list!!!
			Iterator<Element> it = elementsList.iterator();
			while (it.hasNext()) {
				Element x = it.next();
				if (x.equals (e)) {
					it.remove(); // TODO Check what is deletes: all the set of equal instances or only the one we asked for...
					return;
				}
			}
		}
	}
}
