package socman.input;

import socman.model.Direction;

/**
 * Mediates commands from a player to a single listener
 */
public interface CommandMediator {

	public interface Listener {
		public void playerMoved(Direction direction);
		public void playerWaited();	
	}
	
	public void setListener(Listener listner);
	
}
