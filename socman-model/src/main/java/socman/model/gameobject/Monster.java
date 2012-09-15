package socman.model.gameobject;

import java.util.LinkedList;
import java.util.Queue;

import socman.model.Board;
import socman.model.Coordinate;
import socman.model.Direction;
import socman.model.action.Action;
import socman.model.action.KillAction;
import socman.model.action.MovementAction;

/**
 * Stupid monster. Uses some deterministic movement pattern.
 */
public class Monster extends GameActor {

	public enum MovementStyle {
		CLOCKWISE,
		COUNTER_CLOCKWISE
	}
	
	private Direction lastMoveDir;
	private DirectionCycle dirCycle;
	private final int speed;
	
	
	public Monster(Board board, MovementStyle movementStyle, int speed) {
		super(board);
		this.speed = speed;
		this.dirCycle = new DirectionCycle(movementStyle);
		this.lastMoveDir = dirCycle.next();
	}
	
	public Monster at(int x, int y) {
		this.setCoords(x, y);
		return this;
	}
	
	@Override
	public Queue<Action> createActionsForTurn(Direction playerMoveDirection) {
		Queue<Action> events = new LinkedList<Action>();

		Coordinate lastCoord = getCoordninate();
		for (int i=0; i<speed; i++) {
			MovementAction movement = predictMovement(lastCoord);
			
			if (movement != null) {
				events.add(movement);
				lastCoord = Coordinate.valueOf(movement.getToX(), movement.getToY());
				if (board.isSocmanAt(lastCoord)) {
					events.add(new KillAction(board, lastCoord));
				}
			}
		}
		
		
		return events;
	}

	private MovementAction predictMovement(Coordinate coord) {
		
		for (int attempts=0; attempts<Direction.values().length; attempts++) {
			if (canMove(coord, lastMoveDir)) {
				return new MovementAction(this, coord, lastMoveDir.adjust(coord));
			}
			
			lastMoveDir = dirCycle.next();
		}

		return null;
	}
	
	public boolean isAt(int x, int y) {
		return coord.equals(Coordinate.valueOf(x, y));
	}
	
	public int getSpeed() {
		return speed;
	}

	public Direction getMovementDirection() {
		return lastMoveDir;
	}

	private static class DirectionCycle 
	{
		private Direction[] dirs;
		
		private int idx = 0;
	
		public DirectionCycle(MovementStyle movementStyle) {
			if (MovementStyle.CLOCKWISE.equals(movementStyle)) {
				dirs = new Direction[] { Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT };
			} else {
				dirs = new Direction[] { Direction.LEFT, Direction.DOWN, Direction.RIGHT, Direction.UP };
			}
			
		}
		
		public Direction next() {
			idx++;
			if (idx >= dirs.length) {
				idx = 0;
			}
			
			return dirs[idx];
		}
	}

	
}
