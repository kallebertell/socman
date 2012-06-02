package socman.view;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import socman.model.action.Action;
import socman.model.action.Callback;
import socman.model.action.MovementAction;
import socman.model.gameobject.GameActor;
import socman.view.sprite.Sprite;


public class ActionAnimatorTest {

	BoardCanvas mockBoardCanvas;
	ActionAnimator animator;
	
	@Before
	public void setup() {
		mockBoardCanvas = mock(BoardCanvas.class);
		animator = new ActionAnimator(mockBoardCanvas);
	}
	
	@Test
	public void shouldExecuteActionInstantly() {
		Callback mockCallback = mock(Callback.class);
		Action mockAction = mock(Action.class);
		
		animator.executeAction(mockAction, mockCallback);
		
		verify(mockAction, times(1)).execute();
		verify(mockCallback, times(1)).ready(true);
	}
	
	@Test
	public void shouldExecuteMoveActionAsyncronously() {
		Callback mockCallback = mock(Callback.class);
		MovementAction mockMoveAction = mock(MovementAction.class);

		when(mockBoardCanvas.getSpriteByGameObject(any(GameActor.class))).thenReturn(new Sprite(10,10));;

		animator.executeAction(mockMoveAction, mockCallback);
		
		verify(mockMoveAction, times(0)).execute();
		verify(mockCallback, times(0)).ready(true);
		
		animator.update(5000);

		verify(mockMoveAction, times(1)).execute();
		verify(mockCallback, times(1)).ready(true);
	}
}
