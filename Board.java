
/*
 *	This class represents the map
 *	On each case, there is a set of Elements
 *	There is references on the Elements stored in this case
 *	We should implement a singleton pattern
*/

public class Board {
	private Case[][] board;
	private Element element1, element2; // TODO remove this after the tests finishes

	public Board (int length, int width) {
		board = new Case[length][width];
		for (int l=0 ; l<length ; ++l) {
			for (int w=0 ; w<width ; ++w) {
				board[l][w] = new Case (new Position (l, w));
			}
		}
		// I have to initialize each Case with a Position, and no Elements list
		// Be careful: each Position belonging to a case should not be instancied for nothing...
		// + initializations with the reading of the XML file; but here there is just some small initializations like an individual on [0, 0]
		element1 = new Element (board[0][0].getPosition());
		element2 = new Element (board[1][2].getPosition());
		board[0][0].add (element1); // TODO That's a copy of the Position actually!! I need to give the reference of the Position only...
		board[1][2].add (element2);
		move (element1, board[1][2].getPosition());
		element1.attack (element2);
		// Testing the attacks
	}

	public String toString () {
		String rep = "length: " + board.length + " width: " + board[0].length;
		for (int i=0 ; i<board.length ; ++i) {
			for (int j=0 ; j<board[0].length ; ++j) { // Whatif board[0] do not exist?
				rep += "\n\t";
				rep += board[i][j].toString();
			}
		}
		return rep;
	}

	public void move (Element e, Position new_pos) {
		board[0][0].remove (e);
		e.setPosition (new_pos);
		board[new_pos.getX()][new_pos.getY()].add (e);
	}
}
