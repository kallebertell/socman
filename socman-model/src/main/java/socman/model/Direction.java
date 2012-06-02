package socman.model;

import java.util.Random;

public enum Direction {
	UP(0,-1),
	DOWN(0,1),
	RIGHT(1,0),
	LEFT(-1,0);

	private int diffX, diffY;
	
	Direction(int diffX, int diffY) {
		this.diffX = diffX;
		this.diffY = diffY;
	}
	
	public Coordinate adjust(Coordinate coord) {
		return Coordinate.valueOf(coord.getX() + diffX, coord.getY() + diffY);
	}

	public static Direction random() {
		return values()[ new Random().nextInt( Direction.values().length ) ];
	}
	
	public static Direction downOrRight() {
		return new Random().nextInt() % 2 == 0 ? Direction.DOWN : Direction.RIGHT;
	}
}
