package socman.model.gameobject;

import java.util.LinkedList;
import java.util.Queue;

import socman.model.Board;
import socman.model.Coordinate;
import socman.model.Direction;
import socman.model.action.Action;
import socman.model.action.MovementAction;


public class Ghost extends GameActor {

	public enum MovementStyle {
		CLOCKWISE,
		COUNTER_CLOCKWISE
	}
	
	private Direction lastMoveDir;
	private DirectionCycle dirCycle;
	private final int speed;
	
	
	public Ghost(Board board, MovementStyle movementStyle, int speed) {
		super(board);
		this.speed = speed;
		this.dirCycle = new DirectionCycle(movementStyle);
		this.lastMoveDir = dirCycle.next();
	}
	
	public Ghost at(int x, int y) {
		this.setCoords(x, y);
		return this;
	}
	
	@Override
	public Queue<Action> createActions(Direction playerMoveDirection) {
		Queue<Action> events = new LinkedList<Action>();

		Coordinate lastCoord = getCoordninate();
		for (int i=0; i<speed; i++) {
			MovementAction event = predictMovement(lastCoord);
			if (event != null) {
				events.add(event);
				lastCoord = Coordinate.valueOf(event.getToX(), event.getToY());
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
