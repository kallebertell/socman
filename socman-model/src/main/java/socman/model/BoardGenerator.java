package socman.model;

import java.util.Random;

import socman.model.gameobject.Monster.MovementStyle;

/**
 * Generates semi-random boards
 */
public class BoardGenerator {

	public static Board generateRandomBoard(int width, int height) {
		Board board = new Board(width, height);
		
		Random rnd = new Random();
		
		for (int i=1; i< (width*height)/5; i++) {
			board.addWall(rnd.nextInt(width), rnd.nextInt(height), Direction.downOrRight());
		}

		for (int i=1; i<width/2; i++) {
			board.addPill(rnd.nextInt(width), rnd.nextInt(height));
		}

		board.addSocman(width/2, height/2);		
		
		board.addMonster(rnd.nextInt(width), rnd.nextInt(height), MovementStyle.CLOCKWISE, 2);
		board.addMonster(rnd.nextInt(width), rnd.nextInt(height), MovementStyle.COUNTER_CLOCKWISE, 1);
		board.addMonster(rnd.nextInt(width), rnd.nextInt(height), MovementStyle.COUNTER_CLOCKWISE, 3);

		return board;
	}
}
