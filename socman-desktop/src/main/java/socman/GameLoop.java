package socman;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import socman.input.CommandMediator;
import socman.input.CommandMediator.Listener;
import socman.model.Board;
import socman.model.Direction;
import socman.model.action.Action;
import socman.model.action.ActionExecutor;
import socman.model.action.Callback;
import socman.model.gameobject.GameActor;
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
	
	private volatile boolean isProcessingActions = false;
	private volatile boolean isGameRunning = true;
	
	
	public GameLoop(CommandMediator commandMediator, Board board, final View view) {
		this.board = board;
		this.view = view;
		commandMediator.setListener(new Listener() {
			@Override public void playerMoved(Direction direction) {
				synchronized (playerMoveDirections) {
					Logger.info("Requested move in direction "+direction);
					playerMoveDirections.add(direction);
				}
			}

			@Override public void playerWaited() {
				Logger.info("Requested wait.");
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
	
	public void gameLoop() throws InterruptedException {
		
		while (isGameRunning) {
			long beganFrameAt = GameTime.getMillis();
			
			gameUpdate(beganFrameAt-lastFrameUpdatedAt);
			view.draw();
			lastFrameUpdatedAt = GameTime.getMillis();
			fps++;
			
			long updateAndDrawInMillis = GameTime.getMillis() - beganFrameAt;
			long frameTimeLeftInMillis = updatePeriod - updateAndDrawInMillis;
				
			if (frameTimeLeftInMillis < 1) {
				frameTimeLeftInMillis = 1;
			}
			
			logFps(beganFrameAt);
			
			Thread.sleep(frameTimeLeftInMillis);
		}
	}
	
	public void stop() {
		isGameRunning = false;
	}

	private void logFps(long beganFrameAt) {
		if ((beganFrameAt-fpsMeasuredAt) >= fpsLoggingIntervalInMillis) {
			fpsMeasuredAt = GameTime.getMillis();
			Logger.debug(String.format("Fps: %1f.", fps/fpsLoggingInterval));
			fps = 0d;
		}
	}
	
	private void gameUpdate(long timePassed) {
		view.update(timePassed);
		
		synchronized (playerMoveDirections) {
			
			if (!playerMoveDirections.isEmpty() && !isProcessingActions) {
				isProcessingActions = true;
				Direction moveDir = playerMoveDirections.poll();
				new ActionProcessor(moveDir, new LinkedList<Action>(), board.newActorQueue(), view).processActions();
			}
			
		}
	}
	
	private class ActionProcessor implements Callback {

		private final ActionExecutor executor;
		private final Queue<Action> actions;
		private final Queue<GameActor> actors;
		private final Direction playerMoveDirection;
		
		public ActionProcessor(Direction playerMoveDirection, Queue<Action> actions, Queue<GameActor> actors, ActionExecutor executor) {
			this.playerMoveDirection = playerMoveDirection;
			this.actions = actions;
			this.actors = actors;
			this.executor = executor;
		}
		
		public void processActions() {
			
			if (!actions.isEmpty()) {
				executor.executeAction(actions.poll(), this);
			
			} else if (!actors.isEmpty()) {
				new ActionProcessor(playerMoveDirection, actors.poll().createActions(playerMoveDirection), actors, executor).processActions();

			} else {
				GameLoop.this.isProcessingActions = false;

			}
		}
		
		@Override public void ready(boolean shouldContinueExecuting) {
			if (shouldContinueExecuting) {
				processActions();
			
			} else {
				GameLoop.this.isProcessingActions = false;
				
			}
		}
		
	}

}
