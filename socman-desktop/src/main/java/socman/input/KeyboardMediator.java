package socman.input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import socman.model.Direction;

/**
 * Translates keyboard input into commands
 */
public class KeyboardMediator implements KeyListener, CommandMediator {

	private Listener listener;
	
	public KeyboardMediator(Component component) {
		component.addKeyListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_UP) {
			listener.playerMoved(Direction.UP);
		}
		
		if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			listener.playerMoved(Direction.DOWN);
		}
		
		if (event.getKeyCode() == KeyEvent.VK_LEFT) {
			listener.playerMoved(Direction.LEFT);
		}
		
		if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
			listener.playerMoved(Direction.RIGHT);
		}
		
		if (event.getKeyCode() == KeyEvent.VK_SPACE) {
			listener.playerWaited();
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		
	}

	@Override
	public void keyTyped(KeyEvent event) {
		
	}

	@Override
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	
}
