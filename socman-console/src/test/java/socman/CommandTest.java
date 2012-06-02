package socman;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

import socman.model.Board;
import socman.model.Direction;
import socman.model.action.Action;
import socman.model.gameobject.GameActor;


public class CommandTest {

	Board mockBoard;
	
	@Before
	public void setup() {
		mockBoard = mock(Board.class);
	}
	
	@Test
	public void shouldDoNothing() {
		Command.noOp.doCommand(null);
	}
	
	@Test
	public void shouldFetchActionAndExecuteItInReactionToUpMove() {
		LinkedList<GameActor> actorQueue = new LinkedList<GameActor>();
		GameActor mockActor = mock(GameActor.class);
		actorQueue.add(mockActor);
		when(mockBoard.newActorQueue()).thenReturn(actorQueue);
		
		Queue<Action> actionQueue = new LinkedList<Action>();
		Action mockAction = mock(Action.class);
		actionQueue.add(mockAction);
		when(mockActor.createActions(Direction.UP)).thenReturn(actionQueue);
		
		Command.moveUp.doCommand(mockBoard);
		
		verify(mockActor, times(1)).createActions(Direction.UP);
		verify(mockAction, times(1)).execute();
	}
}
