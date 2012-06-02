package socman.util;

public class GameTime {

	public static final long SECOND_IN_MS = 1000l;
	
	public static long getMillis() {
		return System.nanoTime() / 1000000l;
	}
	
}
