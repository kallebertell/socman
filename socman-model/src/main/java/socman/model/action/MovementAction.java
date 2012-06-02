package socman.model.action;

import socman.model.Coordinate;
import socman.model.gameobject.GameActor;

public class MovementAction implements Action {
	
	private final GameActor actor;
	private final Coordinate from;
	private final Coordinate to;

	public MovementAction(GameActor actor, Coordinate from, Coordinate to) {
		this.actor = actor;
		this.from = from;
		this.to = to;
		
	}
	public int getFromX() {
		return from.getX();
	}

	public int getFromY() {
		return from.getY();
	}

	public int getToX() {
		return to.getX();
	}

	public int getToY() {
		return to.getY();
	}
		
	public GameActor getActor() {
		return actor;
	}
	
	@Override
	public void execute() {
		actor.applyMovement(this);
	}
	
	public String toString() {
		return "Move "+getFromX()+","+getFromY()+" to "+getToX()+", "+getToY(); 
	}

	
}
