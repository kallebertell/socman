package socman.view.tween;

import socman.view.sprite.Sprite;

/**
 * Tweens the x and y properties of a Sprite
 */
public class TranslateTween implements Tween {
 
    private Tween.Callback callback;
    
	private Sprite target;
	
	private float xMovement;
	private float yMovement;
	private float targetX;
	private float targetY;
	private float tweenDuration;
	private long timeActive;
	
	private boolean disposed = false;
	
	public TranslateTween(
			Sprite target,
			float moveToX, float moveToY, 
			float duration, 
			Tween.Callback callback) {
		this.target = target;
		this.xMovement = moveToX - target.getX();
		this.yMovement = moveToY - target.getY();
		this.targetX = moveToX;
		this.targetY = moveToY;
		this.tweenDuration = duration;
		this.callback = callback;
	}

	@Override
	public void update(long timePassed) {
		if (disposed) {
			throw new IllegalStateException("Can't update a disposed tween.");
		}
		
		timeActive += timePassed;
		
		if (timeActive >= tweenDuration) {
			dispose();
			return;
		}
		
		float ratio = timePassed/tweenDuration;
		
		float nextX = target.getX() + (xMovement * ratio);
		float nextY = target.getY() + (yMovement * ratio);

		target.setX(nextX);
		target.setY(nextY);
	}
	
	private void dispose() {
		target.setX(targetX);
		target.setY(targetY);
		disposed = true;
		callback.tweenDisposed();
	}
	
	@Override
	public boolean isDisposed() {
		return disposed;
	}

	public float getTargetX() {
		return targetX;
	}
	
	public float getTargetY() {
		return targetY;
	}

	@Override
	public String toString() {
		return target.toString() + " to "+targetX+", "+targetY;
	}
}
