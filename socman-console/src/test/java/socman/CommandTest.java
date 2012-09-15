package socman;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import socman.model.Board;
import socman.model.Direction;
import socman.model.Round;
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
		Round mockRound = mock(Round.class);
		when(mockRound.isFinished()).thenAnswer(new Answer<Boolean>() {
			int cnt = 0;
			@Override public Boolean answer(InvocationOnMock invocation) throws Throwable {
				return (++cnt) > 1;
			}
		});
		
		GameActor mockActor = mock(GameActor.class);
		when(mockRound.getNextActor()).thenReturn(mockActor);
		when(mockBoard.newGameRound()).thenReturn(mockRound);
		
		Queue<Action> actionQueue = new LinkedList<Action>();
		Action mockAction = mock(Action.class);
		actionQueue.add(mockAction);
		when(mockActor.createActionsForTurn(Direction.UP)).thenReturn(actionQueue);
		
		Command.moveUp.doCommand(mockBoard);
		
		verify(mockActor, times(1)).createActionsForTurn(Direction.UP);
		verify(mockAction, times(1)).execute();
	}
}
