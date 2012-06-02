package socman.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import socman.model.gameobject.GameActor;
import socman.model.gameobject.GameObject;
import socman.model.gameobject.Ghost;
import socman.model.gameobject.Socman;
import socman.model.gameobject.Pill;
import socman.model.gameobject.Ghost.MovementStyle;

/**
 * The game board. 
 * 
 */
public class Board {

	public static interface ChangeListener {
		void removed(GameObject gameOb);
		void added(GameObject gameOb);
	}

	
	private Collection<Direction>[][] wallCords;
	
	private List<GameActor> gameChars = new ArrayList<GameActor>();
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private List<Pill> pills = new ArrayList<Pill>();

	private ChangeListener changeListener = new ChangeListener() {
		@Override public void removed(GameObject gameOb) {
			// no-op impl
		}
		@Override public void added(GameObject gameOb) {
			// no-op impl			
		}
	};
	
	@SuppressWarnings("unchecked")
	public Board(int width, int height) {
		wallCords = new HashSet[height][width];
		for (int i=0; i<height; i++) {
			for (int n=0; n<width; n++) {
				wallCords[i][n] = new HashSet<Direction>();
			}
		}		
	}
	
	public boolean isLegalMove(int fromX, int fromY, Direction direction) {
		return getLegalMoves(fromX, fromY).contains(direction) 
				&& !isAnyoneAt(direction.adjust(Coordinate.valueOf(fromX,fromY)));
	}
	
	public boolean isAnyoneAt(Coordinate coord) {
		for (GameActor ghost : gameChars) {
			if (ghost.getCoordninate().equals(coord)) {
				return true;
			}
		}

		return false;
	}

	public int getHeight() {
		return wallCords.length;
	}

	public int getWidth() {
		return wallCords[0].length;
	}

	public void addWall(int x, int y, Direction direction) {
		if (!isLegalWallDirection(direction)) {
			throw new BoardException("Can't add a wall in direction "+direction);
		}
		
		wallCords[y][x].add(direction);
	}
	
	private boolean isLegalWallDirection(Direction direction) {
		return EnumSet.of(Direction.DOWN, Direction.RIGHT).contains(direction);
	}

	public Collection<Direction> getLegalMoves(int x, int y) {
		Collection<Direction> walls = wallCords[y][x];
		
		Collection<Direction> legalMoves = new HashSet<Direction>();
		
		if (x > 0) {
			if (wallCords[y][x-1].contains(Direction.RIGHT))
				walls.add(Direction.LEFT);
		}
		
		if (y > 0) {
			if (wallCords[y-1][x].contains(Direction.DOWN))
				walls.add(Direction.UP);
		}
		
		for (Direction dir : Direction.values()) {
			if (!walls.contains(dir) && !isAnOutOfBoundsMove(x, y, dir)) {
				legalMoves.add(dir);
			}
		}
		
		return legalMoves;
	}
	
	private boolean isAnOutOfBoundsMove(int x, int y, Direction dir) {
		Coordinate coord = dir.adjust(Coordinate.valueOf(x, y));
		
		return 		coord.getX() < 0 
				|| 	coord.getX() > getMaxX()
				||	coord.getY() < 0 
				||	coord.getY() > getMaxY();
	}

	public int getMaxX() {
		return wallCords[0].length-1;
	}
	
	public int getMaxY() {
		return wallCords.length-1;
	}
	
	public Pill addPill(int x, int y) {
		Pill pill = new Pill(this);
		pill.setCoords(x, y);
		gameObjects.add(pill);
		pills.add(pill);
		changeListener.added(pill);
		return pill;
	}
	
	public boolean hasPill(int x, int y) {
		return getPillAt(x, y) != null;
	}

	private Pill getPillAt(int x, int y) {
		for (Pill pill: pills) {
			if (pill.getX() == x && pill.getY() == y) {
				return pill;
			}
		}
		return null;
	}
	
	public void removePill(int x, int y) {
		if (!hasPill(x,y)) {
			throw new BoardException("No pill at "+x+" "+y);
		}

		Pill pill = getPillAt(x, y);
		gameObjects.remove(pill);
		pills.remove(pill);
		changeListener.removed(pill);
	}


	public boolean hasEastWall(int x, int y) {
		return wallCords[y][x].contains(Direction.RIGHT);
	}

	public boolean hasSouthWall(int x, int y) {
		return wallCords[y][x].contains(Direction.DOWN);
	}

	public boolean hasPill(Coordinate coord) {
		return hasPill(coord.getX(), coord.getY());
	}

	/**
	 * Creates a new queue of actors which can be used to process their actions in a turn-based fashion.
	 */
	public Queue<GameActor> newActorQueue() {
		return new LinkedList<GameActor>(gameChars);
	}

	public void setChangeListener(ChangeListener listener) {
		this.changeListener = listener;
	}
	
	public Ghost addGhost(int x, int y, MovementStyle moveStyle, int speed) {
		Ghost ghost = new Ghost(this, moveStyle, speed);
		ghost.setCoords(x, y);
		gameChars.add(ghost);
		gameObjects.add(ghost);
		changeListener.added(ghost);
		return ghost;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public Socman addSocman(int x, int y) {
		Socman socman = new Socman(this);
		socman.setCoords(x, y);
		gameChars.add(socman);
		gameObjects.add(socman);
		changeListener.added(socman);
		return socman;
	}

	
}
