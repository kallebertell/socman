package pacturn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

import socman.model.Board;
import socman.model.Board.ChangeListener;
import socman.model.Direction;
import socman.model.gameobject.GameActor;
import socman.model.gameobject.GameObject;
import socman.model.gameobject.Ghost;
import socman.model.gameobject.Ghost.MovementStyle;
import socman.model.gameobject.Socman;

public class BoardTest {

	private Board board;
	
	@Before
	public void setup() {
		board = new Board(3,3);	
	}
	
	@Test
	public void ensureBoardKnowsLegalMoves() {
		boolean legal = board.isLegalMove(1, 1, Direction.DOWN);
		assertTrue(legal);
	}
	
	@Test
	public void ensureBoardKnowsIllegalMoves() {
		board.addWall(1,1, Direction.DOWN);
		boolean legal = board.isLegalMove(1, 1, Direction.DOWN);
		assertFalse(legal);
	}
	
	@Test
	public void ensureBoardSetupsDimensionsCorrectly() {
		assertEquals(3, board.getWidth());
		assertEquals(3, board.getHeight());
	}
	
	@Test
	public void shouldGetLegalMoves() {
		board.addWall(1, 1, Direction.RIGHT);
		Collection<Direction> legalMoves = board.getLegalMoves(1, 1);
		assertEquals(EnumSet.of(Direction.UP, Direction.DOWN, Direction.LEFT), legalMoves);
	}
	
	@Test
	public void shouldBeAbleToAddPillToBoard() {
		board.addPill(1,1);
		assertTrue(board.hasPill(1,1));
	}
	
	@Test
	public void ensureUpIsIllegalFromHighestRow() {
		assertFalse(board.isLegalMove(0, 0, Direction.UP));
	}
	
	@Test
	public void ensureDownIsIllegalFromLowestRow() {
		assertFalse(board.isLegalMove(2, 2, Direction.DOWN));
	}
	
	@Test
	public void ensureLeftIsIllegalFromFirstColumn() {
		assertFalse(board.isLegalMove(0, 0, Direction.LEFT));
	}
	
	@Test
	public void ensureRightIsIllegalFromLastColumn() {
		assertFalse(board.isLegalMove(2, 2, Direction.RIGHT));
	}
	
	@Test
	public void shouldNotifyOfNewGameObjects() {
		ChangeListener mockChangeListener = mock(ChangeListener.class);
		board.setChangeListener(mockChangeListener);
		board.addPill(1, 2);
		board.addGhost(1, 1, MovementStyle.CLOCKWISE, 2);
		board.addSocman(0, 0);
		verify(mockChangeListener, times(3)).added(any(GameObject.class));
	}
	
	@Test
	public void shouldNotifyOfRemovedGameObjects() {
		ChangeListener mockChangeListener = mock(ChangeListener.class);
		board.setChangeListener(mockChangeListener);
		board.addPill(1, 1);
		board.removePill(1, 1);
		verify(mockChangeListener, times(1)).removed(any(GameObject.class));
	}
	
	@Test
	public void shouldCreateAnActorQuee() {
		Socman socman = board.addSocman(0, 1);
		Ghost ghost = board.addGhost(1, 1, MovementStyle.CLOCKWISE, 2);
		Queue<GameActor> actors = board.newActorQueue();
		assertEquals(2, actors.size());
		assertEquals(socman, actors.poll());
		assertEquals(ghost, actors.poll());
	}
}
