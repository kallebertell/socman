package socman.view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.Graphics2D;

import org.junit.Before;
import org.junit.Test;

import socman.model.Board;
import socman.model.Direction;
import socman.model.gameobject.GameObject;
import socman.view.sprite.Sprite;

public class BoardCanvasTest {

	int scale = 10;
	int width = 10;
	int height = 10;
	BoardCanvas boardCanvas;
	Board board;
	Graphics2D mockG2d;
	
	@Before
	public void setup() {
		board = new Board(width, height);
		boardCanvas = new BoardCanvas(board, scale);
		mockG2d = mock(Graphics2D.class);
	}
	
	@Test
	public void shouldPaintWall() {
		board.addWall(4, 3, Direction.RIGHT);
		boardCanvas.paint(mockG2d);
		verify(mockG2d, times(1)).drawLine(4*scale+scale, 3*scale, 4*scale+scale, 3*scale+scale);
	}
	
	@Test
	public void shouldPaintBorders() {
		boardCanvas.paint(mockG2d);
		verify(mockG2d, times(1)).drawRect(0, 0, width*scale, height*scale);
	}
	
	@Test
	public void shouldPaintSprite() {
		GameObject gameOb = new GameObject(board);
		gameOb.setCoords(2, 5);
		Sprite sprite = new Sprite(2*scale, 5*scale) {
			@Override public void draw(int scale, Graphics2D g2d) {
				g2d.fillOval((int)x, (int)y, scale, scale);
			}
		};
		
		boardCanvas.addGameObjectSprite(gameOb, sprite);
		boardCanvas.paint(mockG2d);
		
		verify(mockG2d, times(1)).fillOval(2*scale, 5*scale, scale, scale);
	}
	
}
