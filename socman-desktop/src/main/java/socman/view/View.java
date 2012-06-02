package socman.view;

import socman.model.action.ActionExecutor;


public interface View extends ActionExecutor {
	
	public void draw();
	public void update(long timePassed);

}
