package socman.model.gameobject;

import java.util.LinkedList;
import java.util.Queue;

import socman.model.Actor;
import socman.model.Board;
import socman.model.Coordinate;
import socman.model.Direction;
import socman.model.action.Action;
import socman.model.action.MovementAction;

/**
 * A game actor is someone who gets a turn in the game.
 * They can move around on the board.
 */
public abstract class GameActor extends GameObject implements Actor {

	public GameActor(Board board) {
		super(board);
	}
	
	public boolean canMove(Direction dir) {
		return board.isLegalMove(coord.getX(), coord.getY(), dir);
	}
	
	public boolean canMove(Coordinate coord, Direction dir) {
		return board.isLegalMove(coord.getX(), coord.getY(), dir);
	}

	public void applyMovement(MovementAction move) {
		setCoords(move.getToX(), move.getToY());
	}

	/**
	 * Returns the actions which should be executed in reaction to the given player command (and current board state).
	 */
	@Override
	public Queue<Action> createActionsForTurn(Direction direction) {
		return new LinkedList<Action>();
	}

}
