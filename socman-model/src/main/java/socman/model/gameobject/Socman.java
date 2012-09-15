package socman.model.gameobject;

import java.util.Queue;

import socman.model.Board;
import socman.model.Coordinate;
import socman.model.Direction;
import socman.model.action.Action;
import socman.model.action.EatPillAction;
import socman.model.action.MovementAction;

/**
 * Our heroic protagonist. Separation of Concerns-Man. 
 * Probably the worst use of imagination ever.
 */
public class Socman extends GameActor {

	public Socman(Board board) {
		super(board);
	}

	@Override
	public Queue<Action> createActionsForTurn(Direction dir) {
		Queue<Action> actions = super.createActionsForTurn(dir);
		
		if (canMove(dir)) {
			Coordinate newCoord = dir.adjust(getCoordninate());
			actions.add(new MovementAction(this, getCoordninate(), newCoord));
			if (board.hasPill(newCoord)) {
				actions.add(new EatPillAction(board, newCoord));
			}
			
		} else {
			// If we can't move we won't let anyone move
			actions.add(Action.cancel);
		}
		
		return actions;
	}

}
