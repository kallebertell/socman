package socman.model;

import java.util.Queue;

import socman.model.action.Action;


public interface Actor {

	/**
	 * Returns the actions which should be executed in reaction to the given player command (and current board state).
	 */
	public Queue<Action> createActionsForTurn(Direction direction);
	
}
