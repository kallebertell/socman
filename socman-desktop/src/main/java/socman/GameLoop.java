package socman;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import socman.input.CommandMediator;
import socman.input.CommandMediator.Listener;
import socman.model.Board;
import socman.model.Direction;
import socman.model.Round;
import socman.model.action.Action;
import socman.model.action.ActionExecutor;
import socman.model.action.Callback;
import socman.util.GameTime;
import socman.util.Logger;
import socman.view.View;

public class GameLoop {

	private static final long updateRate = 100;
	private static final long updatePeriod = GameTime.SECOND_IN_MS / updateRate;

	private static final long fpsLoggingInterval = 10l;
	private static final long fpsLoggingIntervalInMillis = GameTime.SECOND_IN_MS * fpsLoggingInterval; 
	
	private long fpsMeasuredAt;
	
	private Queue<Direction> playerMoveDirections = new ConcurrentLinkedQueue<Direction>();
	private Board board;
	private View view;
	
	private double fps = 0d;
	private long lastFrameUpdatedAt = GameTime.getMillis();
	
	private volatile boolean isProcessingRound = false;
	private volatile boolean isGameRunning = true;
	
	
	public GameLoop(CommandMediator commandMediator, Board board, final View view) {
		this.board = board;
		this.view = view;
		listenToMoveCommands(commandMediator);
	}
	
	private void listenToMoveCommands(CommandMediator commandMediator) {
		commandMediator.setListener(new Listener() {
			@Override public void playerMoved(Direction direction) {
				synchronized (playerMoveDirections) {
					
					if (!GameLoop.this.board.isGameActive()) {
						Logger.info("Ignoring input: "+direction);
						return;
					}
					
					Logger.info("Requested move in direction "+direction);
					playerMoveDirections.add(direction);
				}
			}

		});
	}
	
	public void start() {
		new Thread(new Runnable() {
			@Override public void run() {
				try {
					gameLoop();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}, "GameLoop-Thread").start();
	}
	
	private void gameLoop() throws InterruptedException {
		
		while (isGameRunning) {
			long beganFrameAt = GameTime.getMillis();
			
			gameUpdate(beganFrameAt-lastFrameUpdatedAt);
			view.draw();
			lastFrameUpdatedAt = GameTime.getMillis();
			
			long updateAndDrawInMillis = GameTime.getMillis() - beganFrameAt;
			long frameTimeLeftInMillis = updatePeriod - updateAndDrawInMillis;
				
			if (frameTimeLeftInMillis < 1) {
				frameTimeLeftInMillis = 1;
			}
			
			updateAndLogFps(beganFrameAt);
			
			Thread.sleep(frameTimeLeftInMillis);
		}
	}
	
	public void stop() {
		isGameRunning = false;
	}

	private void updateAndLogFps(long beganFrameAt) {
		fps++;

		if ((beganFrameAt-fpsMeasuredAt) >= fpsLoggingIntervalInMillis) {
			fpsMeasuredAt = GameTime.getMillis();
			Logger.debug(String.format("Fps: %1f.", fps/fpsLoggingInterval));
			fps = 0d;
		}
	}
	
	private void gameUpdate(long timePassed) {
		view.update(timePassed);
		
		synchronized (playerMoveDirections) {
			
			if (!playerMoveDirections.isEmpty() && !isProcessingRound) {
				isProcessingRound = true;
				startNewRound(playerMoveDirections.poll());
			}
			
		}
	}
	
	private void startNewRound(Direction playerMoveDirection) {
		new RoundProcessor(playerMoveDirection, new LinkedList<Action>(), board.newGameRound(), view).processActions();
	}
	
	/**
	 * Resolves a game round.
	 */
	private class RoundProcessor implements Callback {

		private final ActionExecutor executor;
		private final Queue<Action> actions;
		private final Round round;
		private final Direction playerMoveDirection;
		
		public RoundProcessor(Direction playerMoveDirection, Queue<Action> actions, Round round, ActionExecutor executor) {
			this.playerMoveDirection = playerMoveDirection;
			this.actions = actions;
			this.round = round;
			this.executor = executor;
		}
		
		public void processActions() {
			if (!actions.isEmpty()) {
				executor.executeAction(actions.poll(), this);
			
			} else if (!round.isFinished()) {
				Queue<Action> nextActions =  round.getNextActor().createActionsForTurn(playerMoveDirection);
				new RoundProcessor(playerMoveDirection, nextActions, round, executor).processActions();

			} else {
				GameLoop.this.isProcessingRound = false;

			}
		}
		
		@Override public void ready(boolean shouldContinueExecuting) {			
			if (shouldContinueExecuting) {
				processActions();
			
			} else {
				GameLoop.this.isProcessingRound = false;
				
			}
		}
		
	}

}
