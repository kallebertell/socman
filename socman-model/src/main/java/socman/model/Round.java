package socman.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * In a game round each actor gets to do actions on their respective turn.
 */
public class Round {

	private final Queue<Actor> actors;
	
	public Round(Collection<? extends Actor> actors) {
		this.actors = new LinkedList<Actor>(actors);	
	}
	
	public Actor getNextActor() {
		return actors.poll();
	}
	
	public boolean isFinished() {
		return actors.isEmpty();
	}
	
}
