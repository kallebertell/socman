package socman.model.action;

import socman.model.Board;
import socman.model.Coordinate;
import socman.util.Logger;

public class KillAction implements Action {

	private final Coordinate coord;
	private final Board board;

	public KillAction(Board board, Coordinate coord) {
		this.board = board;
		this.coord = coord;
	}

	@Override
	public void execute() {
		Logger.info("Killing at "+coord);
		board.setStatus(Board.Status.GAME_LOST);
	}

	@Override
	public String toString() {
		return "Kill at "+coord; 
	}

}
