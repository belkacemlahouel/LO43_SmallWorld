package kernel;

import kernel.individuals.Individual;
import java.util.ArrayList;

/***************************************************************
 *  @author Belkacem Lahouel - UTBM - A2013
 *  Project: LO43, Small World
 ***************************************************************
 *	This class just represents the map
 *	On each Case, there is a set of Elements
 *	There is references on the Elements stored inside each case
 *	We should implement a Singleton Pattern
*/

public class Board {
	private Case[][] board;
	
	public ArrayList<Case> getAround (Individual e) {
		ArrayList<Case> rep = new ArrayList<Case> (0);
		int min_x = Math.min(board.length, Math.max((e.getPosition().getX()-e.getVision()), 0)),
			min_y = Math.min(board.length, Math.max((e.getPosition().getY()-e.getVision()), 0)), // check
			max_x = Math.max(0, Math.min((e.getPosition().getX()+e.getVision()), board.length)),
			max_y = Math.max(0, Math.min((e.getPosition().getY()+e.getVision()), board[0].length));
		
		for (int i=min_x ; i<max_x ; ++i) {
			for (int j=min_y ; j<max_y ; ++j) {
				if (Tools.distance(e.getPosition(), get(i, j).getPosition()) < e.getVision() &&
					get(i, j).getElementsList() != null && !get(i, j).getElementsList().isEmpty()) {
					
					rep.add(get (i, j));
				}
			}
		}
		
		return rep;
	}
	
	/*	
	 *	Constructor
	 *	I have to initialize each Case with a Position, and no elements in the element list
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
	 *	Firstly we move on x, then on y
	*/
	public Position getNextPosition (Individual e, Position final_pos) {
		int tmp_x = e.getPosition().getX(), tmp_y = e.getPosition().getY(), tmp_dist = 0;

		if ((Math.abs(e.getPosition().getX() - final_pos.getX()) > 0 || Math.abs(e.getPosition().getY() - final_pos.getY()) > 0)) {
			
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
			return final_pos;
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

	@Override
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
