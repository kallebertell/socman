package socman.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import socman.model.Board;
import socman.model.gameobject.GameObject;
import socman.view.sprite.Sprite;

/**
 * Renders the board and sprites based on the given board model.
 */
@SuppressWarnings("serial")
public class BoardCanvas extends JPanel {
	
	private Board board;
	
	private int scale = 10;
	private int height = 10;
	private int width = 10;
		
	private List<Sprite> sprites = new ArrayList<Sprite>();
	
	private Map<GameObject, Sprite> gameObToSprite = new HashMap<GameObject, Sprite>();
	
	
	public BoardCanvas(Board board, int scale) {
		this.board = board;
		this.width = board.getWidth();
		this.height = board.getHeight();
		this.scale = scale;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;

		drawBorders(g2d);
	    drawWalls(g2d);
	    drawSprites(g2d);
	}

	private void drawBorders(Graphics2D g2d) {
	    g2d.setColor(Color.WHITE);
	    g2d.fillRect(0, 0, width*scale, height*scale);
	    g2d.setColor(Color.BLACK);	    
	    g2d.drawRect(0, 0, width*scale, height*scale);
	}

	private void drawWalls(Graphics2D g2d) {
		for (int y=0; y<height; y++) {
	    	for (int x=0; x<width; x++) {
	    	    if (board.hasEastWall(x,y)) {
	    	    	g2d.drawLine(x*scale+scale, y*scale, x*scale+scale, y*scale+scale);
	    	    }
	    	    
	    	    if (board.hasSouthWall(x,y)) {
	    	    	g2d.drawLine(x*scale, y*scale+scale, x*scale+scale, y*scale+scale);
	    	    }
	    	}
	    }
	}
	
	private void drawSprites(Graphics2D g2d) {
		for (Sprite sprite : sprites) {
	    	sprite.draw(scale, g2d);
	    }
	}
	
	public void addGameObjectSprite(GameObject gameOb, Sprite sprite) {
		sprites.add(sprite);
		gameObToSprite.put(gameOb, sprite);
	}
	
	public Sprite getSpriteByGameObject(GameObject gameOb) {
		return gameObToSprite.get(gameOb);
	}

	public void update(long timePassed) {
		for (Sprite sprite : sprites) {
			sprite.update(timePassed);
		}
	}

	public void removeGameObjectSprite(GameObject gameOb) {
		Sprite sprite = gameObToSprite.get(gameOb);
		sprites.remove(sprite);
		gameObToSprite.remove(gameOb);
	}

	public int getScale() {
		return scale;
	}
}
