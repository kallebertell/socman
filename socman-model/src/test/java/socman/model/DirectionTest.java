package socman.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import socman.model.Coordinate;
import socman.model.Direction;

public class DirectionTest {

	@Test
	public void shouldAdjustXandY() {
		Coordinate coord = Coordinate.valueOf(1,1);
		Coordinate adjusted = Direction.UP.adjust(coord);
		assertEquals(Coordinate.valueOf(1,0), adjusted);
	}
}
