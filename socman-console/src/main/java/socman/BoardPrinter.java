package socman;

import java.io.PrintStream;
import java.util.List;

import socman.model.Board;
import socman.model.gameobject.GameObject;

/**
 * Prints board, e.g. like this (excluding coordinates):
 * +---------+
 * |    x  | |0
 * | + + + + |
 * |  x|s    |1
 * | +-+ +-+ |
 * |  o      |2
 * +---------+
 *  0 1 2 3 4
 */
public class BoardPrinter {

	private final Board board;
	private final PrintStream printStream;

	public BoardPrinter(Board board, PrintStream printStream) {
		this.board = board;
		this.printStream = printStream;
	}
	
	public void print() {
		String horizontalWall = String.format("%1$-" + board.getWidth() + "s", "+").replace(" ", "--") + "-+\n";

		String boardStr = horizontalWall;
		
		List<GameObject> gameObs = board.getGameObjects();
		
		for (int y=0; y<board.getHeight(); y++) {
			boardStr += buildActorRow(gameObs, y);
	
			if (y == board.getHeight()-1) {
				continue;
			}
			
			boardStr += buildWallRow(y);
		}
		
		boardStr += horizontalWall;

		printStream.println(boardStr);
	}

	/**
	 * Build a row which can contains horizontal walls. (+ vertical walls at the left/right ends)
	 */
	private String buildWallRow(int y) {
		String row = "";
		
		for (int x=0; x<board.getWidth(); x++) {
			if (x == 0) {
				row += "|";
			}
				
			if (board.hasSouthWall(x, y)) {
				row += "-";
			} else {
				row += " ";
			}
			
			if (x == board.getWidth()-1) {
				row += "|\n";
			
			} else {	
				row += "+";
			}
		}
		
		return row;
	}

	/**
	 * Builds a row which can contain game actors and vertical walls
	 */
	private String buildActorRow(List<GameObject> gameObs, int y) {
		String row = "";
		
		for (int x=0; x<board.getWidth(); x++) {
			if (x == 0) {
				row += "|";
			}
			
			row += findChar(gameObs, x, y);
			
			if (board.hasEastWall(x, y) || x == board.getWidth()-1) {
				row += "|";
			} else {	
				row += " ";
			}
			
			if (x == board.getWidth()-1) {
				row += "\n";
			
			} 
		}
		
		return row;
	}
	
	private char findChar(List<GameObject> gameObs, int x, int y) {
		for (GameObject gameOb : gameObs) {
			if (gameOb.getX() == x && gameOb.getY() == y) {
				return gameOb.getClass().getSimpleName().charAt(0);
			}
		}
		
		return ' ';
	}
	
}
