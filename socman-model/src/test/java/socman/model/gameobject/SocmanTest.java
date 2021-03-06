package socman.model.gameobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

import socman.model.Board;
import socman.model.Coordinate;
import socman.model.Direction;
import socman.model.action.Action;
import socman.model.action.EatPillAction;
import socman.model.action.MovementAction;


public class SocmanTest {
	Board mockBoard;
	Socman socman;
	
	@Before
	public void setup() {
		mockBoard = mock(Board.class);
		socman = new Socman(mockBoard);
		when(mockBoard.isLegalMove(anyInt(), anyInt(), any(Direction.class))).thenReturn(true);
	}
	

	@Test
	public void shouldBeAbleToSetCoords() {
		socman.setCoords(2, 2);
		assertEquals(2, socman.getX().intValue());
		assertEquals(2, socman.getY().intValue());
	}
	
	@Test
	public void shouldBeAbleToMoveUp() {
		socman.setCoords(1, 1);
		assertTrue(socman.canMove(Direction.UP));
	}
	
	@Test
	public void shouldBeAbleToMoveDown() {
		socman.setCoords(1, 1);
		assertTrue(socman.canMove(Direction.DOWN));
	}
	
	@Test
	public void shouldNotBeAbleToMoveUp() {
		when(mockBoard.isLegalMove(1, 1, Direction.UP)).thenReturn(false);
		socman.setCoords(1, 1);
		assertFalse(socman.canMove(Direction.UP));
	}
	
	@Test
	public void shouldCreateMovementAction() {
		socman.setCoords(1, 1);
		
		Queue<Action> events = socman.createActionsForTurn(Direction.LEFT);
		
		MovementAction event = (MovementAction)events.poll();
		assertEquals(1, event.getFromX());
		assertEquals(1, event.getFromY());
		assertEquals(0, event.getToX());
		assertEquals(1, event.getToY());
	}
	
	@Test
	public void shouldCreateCancelAction() {
		when(mockBoard.isLegalMove(anyInt(), anyInt(), any(Direction.class))).thenReturn(false);
		Queue<Action> actions = socman.createActionsForTurn(Direction.RIGHT);		
		assertEquals(Action.cancel, actions.poll());
	}
	
	@Test
	public void shouldCreateEatPillAction() {
		when(mockBoard.hasPill(any(Coordinate.class))).thenReturn(true);
		
		Queue<Action> actions = socman.createActionsForTurn(Direction.RIGHT);
		
		while (!actions.isEmpty()) {
			if (actions.poll().getClass().equals(EatPillAction.class)) {
				return;
			}
		}

		fail("Didn't find an EatPillAction");
	}
}
