package socman.model.gameobject;

import java.util.Queue;

import socman.model.Board;
import socman.model.Coordinate;
import socman.model.Direction;
import socman.model.action.Action;
import socman.model.action.EatPillAction;
import socman.model.action.MovementAction;

public class Socman extends GameActor {

	public Socman(Board board) {
		super(board);
	}

	@Override
	public Queue<Action> createActions(Direction dir) {
		Queue<Action> events = super.createActions(dir);
		
		if (canMove(dir)) {
			Coordinate newCoord = dir.adjust(getCoordninate());
			events.add(new MovementAction(this, getCoordninate(), newCoord));
			if (board.hasPill(newCoord)) {
				events.add(new EatPillAction(board, newCoord));
			}
		
		} else {
			// If we can't move we won't let anyone move
			events.add(Action.cancel);
		}
		
		return events;
	}

}
