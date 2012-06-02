package socman.model.gameobject;

import java.util.LinkedList;
import java.util.Queue;

import socman.model.Board;
import socman.model.Coordinate;
import socman.model.Direction;
import socman.model.action.Action;
import socman.model.action.MovementAction;

public class GameActor extends GameObject {

	public GameActor(Board board) {
		super(board);
	}
	
	public boolean canMove(Direction dir) {
		return board.isLegalMove(coord.getX(), coord.getY(), dir);
	}
	
	public boolean canMove(Coordinate coord, Direction dir) {
		return board.isLegalMove(coord.getX(), coord.getY(), dir);
	}

	public void applyMovement(MovementAction mevent) {
		setCoords(mevent.getToX(), mevent.getToY());
	}

	public Queue<Action> createActions(Direction playerMoveDirection) {
		return new LinkedList<Action>();
	}

}
