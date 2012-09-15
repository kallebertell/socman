package socman.model.action;

import socman.util.Logger;

/**
 * An action transfers the game into a new state once its execute method is called.
 */
public interface Action {

	/**
	 * An actor may produce this cancel action if it wants to cancel any subsequent action execution behind it in the queue.
	 */
	public static final Action cancel = new Action() {
		@Override public void execute() {
			Logger.info("Cancel Action Executed.");
		}
	};
	
	
	public void execute();
	
}
