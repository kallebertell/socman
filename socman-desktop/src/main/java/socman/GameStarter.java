package socman;

import socman.input.KeyboardMediator;
import socman.model.Board;
import socman.model.BoardGenerator;
import socman.view.GameWindow;

/** 
 * Starts the game loop.
 */
public class GameStarter {

	private final static int scale = 40;
	
	public static void main(String[] args) {
		Board board = BoardGenerator.generateRandomBoard(10, 10);
		GameWindow window = new GameWindow(scale, board);
		new GameLoop(new KeyboardMediator(window), board, window).start();
	}

}
