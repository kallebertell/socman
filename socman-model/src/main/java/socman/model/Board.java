package socman.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import socman.model.action.Action;
import socman.model.gameobject.GameActor;
import socman.model.gameobject.GameObject;
import socman.model.gameobject.Monster;
import socman.model.gameobject.Monster.MovementStyle;
import socman.model.gameobject.Pill;
import socman.model.gameobject.Socman;

/** 
 * A two dimensional board with a width and a height.
 * Actors and Objects can be placed on the board.
 * 
 */
public class Board {

	public static enum Status {
		GAME_ACTIVE,
		GAME_LOST,
		GAME_WON;
	}
	
	public static interface ChangeListener {
		void removed(GameObject gameOb);
		void added(GameObject gameOb);
	}

	private Status status = Status.GAME_ACTIVE;
	private Collection<Direction>[][] wallCords;
	
	private List<GameActor> gameActors = new ArrayList<GameActor>();
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private List<Pill> pills = new ArrayList<Pill>();

	/** This guy will take the last turn in a round and update game state. */
	private final Actor gameJudge = new Actor() {
		@Override public Queue<Action> createActionsForTurn(Direction direction) {
			Queue<Action> actions = new LinkedList<Action>();
			
			if (Board.this.getPillAmount() < 1) {
				actions.add(new Action() {
					@Override public void execute() {
						setStatus(Status.GAME_WON);
					}
				});
			}
			
			return actions;
		}
	};
	
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
				&& !isMonsterAt(direction.adjust(Coordinate.valueOf(fromX,fromY)));
	}
	
	public boolean isMonsterAt(Coordinate coord) {
		for (GameActor actor : gameActors) {
			if (!(actor instanceof Monster)) {
				continue;
			}
			
			if (actor.getCoordninate().equals(coord)) {
				return true;
			}
		}

		return false;
	}
	
	public boolean isAnyoneAt(Coordinate coord) {
		for (GameActor ghost : gameActors) {
			if (ghost.getCoordninate().equals(coord)) {
				return true;
			}
		}

		return false;
	}
	
	public boolean isSocmanAt(Coordinate coord) {
		for (GameActor actor : gameActors) {
			if (actor instanceof Socman) {
				return actor.getCoordninate().equals(coord);
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
	
	public boolean hasPill(Coordinate coord) {
		return hasPill(coord.getX(), coord.getY());
	}
	
	private boolean hasPill(int x, int y) {
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
			throw new BoardException("No pill at "+x+","+y);
		}

		Pill pill = getPillAt(x, y);
		gameObjects.remove(pill);
		pills.remove(pill);
		changeListener.removed(pill);
	}
	
	public int getPillAmount() {
		return pills.size();
	}


	public boolean hasEastWall(int x, int y) {
		return wallCords[y][x].contains(Direction.RIGHT);
	}

	public boolean hasSouthWall(int x, int y) {
		return wallCords[y][x].contains(Direction.DOWN);
	}

	/**
	 * Creates a new game round from where we can get actors in order so they can do their stuff in a turn-based fashion.
	 */
	public Round newGameRound() {
		List<Actor> actors = new ArrayList<Actor>( gameActors );
		actors.add(gameJudge);
		return new Round(actors);
	}

	public void setChangeListener(ChangeListener listener) {
		this.changeListener = listener;
	}
	
	public Monster addMonster(int x, int y, MovementStyle moveStyle, int speed) {
		Monster monster = new Monster(this, moveStyle, speed);
		monster.setCoords(x, y);
		gameActors.add(monster);
		gameObjects.add(monster);
		changeListener.added(monster);
		return monster;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public Socman addSocman(int x, int y) {
		Socman socman = new Socman(this);
		socman.setCoords(x, y);
		gameActors.add(socman);
		gameObjects.add(socman);
		changeListener.added(socman);
		return socman;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public boolean isGameActive() {
		return Status.GAME_ACTIVE.equals(getStatus());
	}

	


}
