package socman;

import java.util.Queue;

import socman.model.Actor;
import socman.model.Board;
import socman.model.Direction;
import socman.model.Round;
import socman.model.action.Action;

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
				doGameRound(dir, board);			
			}
		};
	}
	
	private static void doGameRound(Direction dir, Board board) {
		Round round = board.newGameRound();
		
		// Since this is all synchronous we don't need any of the action callback mumbo-jumbo
		while (!round.isFinished()) {
			Actor actor = round.getNextActor();
			Queue<Action> actions = actor.createActionsForTurn(dir);
			for (Action action : actions) {
				action.execute();
			}
		}
	}
	
}
