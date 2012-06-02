package socman.model.action;

/**
 * A callback provided given to an ActionExecutor.
 * The executor should call the ready method when the action has executed.
 */
public interface Callback {

	public void ready(boolean continueExecuting);
	
}
