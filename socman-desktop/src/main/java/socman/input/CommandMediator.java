package socman.input;

import socman.model.Direction;

/**
 * Mediates commands from a player to a single listener
 */
public interface CommandMediator {

	/** Listens to commands, i.e. directions in which the player requested movement */
	public interface Listener {
		public void playerMoved(Direction direction);
	}
	
	public void setListener(Listener listner);
	
}
