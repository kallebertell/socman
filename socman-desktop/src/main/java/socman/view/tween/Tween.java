package socman.view.tween;

/**
 * For InbeTween animations. I.e. properties which are incremented over time.
 * 
 */
public interface Tween {
 
	/**
	 * Callback interface to be called once a tween has finished animating its property.
	 */
	public interface Callback {
		public void tweenDisposed();
	}
	
	public void update(long timePassed);
	public boolean isDisposed();
	
}
