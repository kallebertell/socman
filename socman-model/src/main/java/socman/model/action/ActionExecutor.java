package socman.model.action;


public interface ActionExecutor {

	/**
	 * Executes an action asynchronously
	 * 
	 * @param action The action to execute
	 * @param callback Callback to call when action has been executed
	 */
	public void executeAction(Action action, Callback callback);

}
