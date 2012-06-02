package socman.input;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.Component;
import java.awt.event.KeyEvent;

import org.junit.Before;
import org.junit.Test;

import socman.model.Direction;

public class KeyboardMediatorTest {

	KeyboardMediator keyboardMediator;
	Component mockComponent;
	CommandMediator.Listener mockListener;
	
	@Before
	public void setup() {
		mockComponent = mock(Component.class);
		mockListener = mock(CommandMediator.Listener.class);
		
		keyboardMediator = new KeyboardMediator(mockComponent);
		keyboardMediator.setListener(mockListener);
	}

	@Test
	public void shouldListen() {
		verify(mockComponent, times(1)).addKeyListener(keyboardMediator);
	}
	
	@Test
	public void shouldMediateUp() {
		keyboardMediator.keyPressed(new KeyEvent(mockComponent, 123, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'u'));
		verify(mockListener, times(1)).playerMoved(Direction.UP);
	}
	
	@Test
	public void shouldMediateDown() {
		keyboardMediator.keyPressed(new KeyEvent(mockComponent, 123, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'd'));
		verify(mockListener, times(1)).playerMoved(Direction.DOWN);
	}
	
}
