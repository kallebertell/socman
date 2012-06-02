package socman.model.action;

import socman.model.Board;
import socman.model.Coordinate;
import socman.util.Logger;

public class EatPillAction implements Action {

	private final Coordinate coord;
	private final Board board;

	public EatPillAction(Board board, Coordinate coord) {
		this.board = board;
		this.coord = coord;
		
	}

	@Override
	public void execute() {
		Logger.info("Eating pill at "+coord);
		board.removePill(coord.getX(), coord.getY());
	}

	@Override
	public String toString() {
		return "Eat Pill at "+coord; 
	}

}
