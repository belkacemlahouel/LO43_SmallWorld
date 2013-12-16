
/*
 *	This class represents the map
 *	On each case, there is a set of Elements
 *	There is references on the Elements stored in this case
 *	We should implement a singleton pattern
*/

public class Board {
	private Case[][] board;

	/*
	 *	Constructor
	 *	I have to initialize each Case with a Position, and no Elements list
	 *	The one time Positions are instancied is here, and only here
	 *	Initializations afterwards (when the XML parser is complete) 
	*/
	public Board (int length, int width) {
		board = new Case[length][width];
		for (int l=0 ; l<length ; ++l) {
			for (int w=0 ; w<width ; ++w) {
				board[l][w] = new Case (new Position (l, w));
			}
		}
	}

	public Case[][] getBoard() {
		return board;
	}

	public void setBoard(Case[][] board) {
		this.board = board;
	}

	/*
	 *	Should add a method to get a direct Position from a starting Position and a reach, in one direction
	 *	Firstly we move on x, then on y (for the Human, but it might be different for the other Individuals)
	*/
	public Position getNextPosition (Individual e, Position final_pos) {
		int tmp_x = e.getPosition().getX(), tmp_y = e.getPosition().getY(), tmp_dist = 0;

		if ((Math.abs(e.getPosition().getX() - final_pos.getX()) > 0 || Math.abs(e.getPosition().getY() - final_pos.getY()) > 0)) {
			// && final_pos.getX() < board.length && final_pos.getY() < board[0].length) {

			while (tmp_x != final_pos.getX() && tmp_dist < e.getReach()) { // It cannot be < 0, only == 0

				tmp_x += (final_pos.getX() - tmp_x)/(Math.abs(final_pos.getX() - tmp_x)); // We get closer to the final position, careful to the sign...
				tmp_dist += 1;
			}

			while (tmp_y != final_pos.getY() && tmp_dist < e.getReach()) { // It cannot be < 0, only == 0

				tmp_y += (final_pos.getY() - tmp_y)/(Math.abs(final_pos.getY() - tmp_y)); // We get closer to the final position, careful to the sign...
				tmp_dist += 1;
			}

			return get(tmp_x, tmp_y).getPosition();

		} else { // therefore, if we are here we should already be on final_pos (Position) and nothing to do...
			return final_pos; // What if the guy is actually blocked? TODO
		}
	}
	
	/*
	 *	Returns a random Position belonging to the Board
	*/
	public Position randPosition () {
		int rand_x = Tools.rand (board.length, 0), rand_y = Tools.rand (board[0].length, 0);
		return board[rand_x][rand_y].getPosition ();
	}

	/*
	 *	Getters
	*/
	public Case get (int i, int j) {
		return board[i][j];
	}

	public Case get (Position pos) {
		return board[pos.getX()][pos.getY()];
	}

	/*
	 *	@Override toString function
	*/
	public String toString () {
		String rep = "length: " + board.length + " width: " + board[0].length;
		for (int i=0 ; i<board.length ; ++i) {
			for (int j=0 ; j<board[0].length ; ++j) { // Whatif board[0] do not exist? TODO
				rep += "\n\t";
				rep += board[i][j].toString();
			}
		}
		return rep;
	}
}
