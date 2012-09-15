package socman.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import socman.model.gameobject.GameActor;


public class RoundTest {

	@Test
	public void shouldGiveActorsInOrder() {
		GameActor mockActor1 = mock(GameActor.class);
		GameActor mockActor2 = mock(GameActor.class);
		List<GameActor> actors = new ArrayList<GameActor>();
		actors.add(mockActor1);
		actors.add(mockActor2);
		
		Round round = new Round(actors);
		
		assertEquals(mockActor1, round.getNextActor());
		assertEquals(mockActor2, round.getNextActor());
	}
	
	@Test
	public void ensureNoActorsMeansRoundIsFinished() {
		assertTrue(new Round(new ArrayList<GameActor>()).isFinished());
	}
}
