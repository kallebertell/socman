package socman;

import java.util.Queue;

import socman.model.Board;
import socman.model.Direction;
import socman.model.action.Action;
import socman.model.gameobject.GameActor;

public abstract class Command {

	public static final Command noOp = new Command() {
		@Override public void doCommand(Board board) {
			System.out.println("Unknown command");
		}
	};
	public static final Command moveUp = newMoveCommand(Direction.UP);
	public static final Command moveDown = newMoveCommand(Direction.DOWN);
	public static final Command moveLeft = newMoveCommand(Direction.LEFT);
	public static final Command moveRight = newMoveCommand(Direction.RIGHT);
	public static final Command quit = new Command() {
		@Override public void doCommand(Board board) {
			System.out.println("Thanks for playing!");
			System.exit(0);
		}
	};
	
	public abstract void doCommand(Board board);
	
	private static Command newMoveCommand(final Direction dir) {
		return new Command() {
			@Override public void doCommand(Board board) {
				doTurn(dir, board);			
			}
		};
	}
	
	private static void doTurn(Direction dir, Board board) {
		Queue<GameActor> actors = board.newActorQueue();
	
		for (GameActor actor : actors) {
			Queue<Action> actions = actor.createActions(dir);
			for (Action action : actions) {
				action.execute();
			}
		}
	}
	
}
