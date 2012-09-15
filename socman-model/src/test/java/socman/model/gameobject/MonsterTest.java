package socman.model.gameobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static socman.model.Coordinate.coord;

import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

import socman.model.Board;
import socman.model.Direction;
import socman.model.action.Action;
import socman.model.action.KillAction;
import socman.model.action.MovementAction;
import socman.model.gameobject.Monster.MovementStyle;

public class MonsterTest {

	Board mockBoard;
	Monster monster;
	
	@Before
	public void setup() {
		mockBoard = mock(Board.class);
		monster = new Monster(mockBoard, MovementStyle.CLOCKWISE, 2);
	}
	

	@Test
	public void shouldCreateTwoMovementActions() {
		when(mockBoard.isLegalMove(anyInt(), anyInt(), any(Direction.class))).thenReturn(true);
		
		Queue<Action> actions = monster.createActionsForTurn(Direction.RIGHT);
		
		assertEquals(MovementAction.class, actions.poll().getClass());
		assertEquals(MovementAction.class, actions.poll().getClass());
		assertNull(actions.poll());
	}
	
	@Test
	public void shouldCreateKillAction() {
		when(mockBoard.isLegalMove(anyInt(), anyInt(), any(Direction.class))).thenReturn(true);
		when(mockBoard.isSocmanAt(coord(2, 1))).thenReturn(true);
		monster.setCoords(1, 1);

		Queue<Action> actions = monster.createActionsForTurn(Direction.RIGHT);
		
		assertEquals(MovementAction.class, actions.poll().getClass());		
		assertEquals(KillAction.class, actions.poll().getClass());
	}
	
}
