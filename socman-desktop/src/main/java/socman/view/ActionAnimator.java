package socman.view;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import socman.model.action.Action;
import socman.model.action.Callback;
import socman.model.action.MovementAction;
import socman.view.sprite.Sprite;
import socman.view.tween.TranslateTween;
import socman.view.tween.Tween;

/**
 * Animates actions.
 * Knows which tween to use for respective action and queues these for animations and so on.
 * 
 */
public class ActionAnimator {
	private static float ANIMATION_DURATION = 150f;
	
	private BoardCanvas boardCanvas;
	
	private Queue<Tween> tweens = new ConcurrentLinkedQueue<Tween>();
	private Tween activeTween;

	private int scale;
	
	public ActionAnimator(BoardCanvas boardCanvas) {
		this.boardCanvas = boardCanvas;
		this.scale = boardCanvas.getScale();
	}
	
	public void executeAction(final Action action, final Callback actionExecutedCallback) {		
		Tween.Callback tweenCallback = new Tween.Callback() {
			@Override public void tweenDisposed() {
				action.execute();
				boolean shouldContinueExecuting = (action != Action.cancel);
				actionExecutedCallback.ready(shouldContinueExecuting);
			}
		};


		if (action instanceof MovementAction) {
			MovementAction moveAction = (MovementAction)action;
			Sprite sprite = boardCanvas.getSpriteByGameObject(moveAction.getActor());			
			TranslateTween tween = new TranslateTween(sprite, moveAction.getToX()*scale, moveAction.getToY()*scale, ANIMATION_DURATION, tweenCallback);
			tweens.add(tween);			
		
		} else {

			tweenCallback.tweenDisposed();
		}

	}

	public void update(long timePassed) {
	
		if (activeTween == null) {
			activeTween = tweens.poll();
			if (activeTween == null) {
				return;
			}
		}
		
		activeTween.update(timePassed);
		
		if (activeTween.isDisposed()) {
			activeTween = null;
		}
	}
}
