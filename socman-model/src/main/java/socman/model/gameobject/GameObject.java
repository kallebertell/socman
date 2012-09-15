package socman.model.gameobject;

import socman.model.Board;
import socman.model.Coordinate;

/**
 * A game object. It can be placed on the board and has some coordinates.
 */
public class GameObject {
	
	protected Board board;
	protected Coordinate coord;

	public GameObject(Board board) {
		this.coord = Coordinate.valueOf(0,0);
		this.board = board;
	}
	
	public void setCoords(int x, int y) {
		coord = Coordinate.valueOf(x, y);
	}

	public Integer getX() {
		return coord.getX();
	}

	public Integer getY() {
		return coord.getY();
	}

	public Coordinate getCoordninate() {
		return coord;
	}

}
