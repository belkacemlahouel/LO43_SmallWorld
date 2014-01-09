package kernel;

import java.util.ArrayList;
import java.util.Iterator;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 *	Class managing the Cases
 *	A Case has one Position and is stored in the Board
*/

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

	public Position getPosition () {
		return pos;
	}

	@Override
	public String toString () {
		String rep = "" + pos;
		if (elementsList != null && !elementsList.isEmpty ()) {
			for (Element e : elementsList) { // What if elementsList is empty?
				rep += "\n\t\t";
				rep += e;
			}
		} else rep += "\n\t\tNothing here";
		
		return rep;
	}

	public void add (Element e) {
		elementsList.add(e);
	}
	
	public synchronized void buryDeads () { // for all Elements
		Iterator<Element> it = elementsList.iterator();
		while (it.hasNext()) {
			Element x = it.next();
			if (x.isDead()) {
				it.remove();
				return ; // assuming there's only one kind of instance of Element, all differents...
			}
		}
	}

	// Remove the first Element encountered in the elementsList which is the same as the one we want to delete. TODO check if there's only one like this
	public synchronized void remove (Element e) {
		if (elementsList != null && !elementsList.isEmpty()) {
			Iterator<Element> it = elementsList.iterator();
			while (it.hasNext()) {
				Element x = it.next();
				if (x.equals (e)) {
					it.remove();
					return;
				}
			}
		}
	}
}
