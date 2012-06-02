package socman.util;

/**
 * Simple logger suitable for the purposes of a small game.
 */
public class Logger {

	public enum Level {
		ERROR,
		INFO,
		DEBUG
	}
	
	private static Level level = Level.DEBUG;	
	
	private static long startTime = System.currentTimeMillis();
	
	
	public static void debug(String msg) {
		if (level.ordinal() >= Level.DEBUG.ordinal()) {
			print("[D] "+msg);
		}
	}
	
	public static void info(String msg) {
		if (level.ordinal() >= Level.INFO.ordinal()) {
			print("[I] "+msg);
		}
	}
	
	public static void error(String msg) {
		error(msg, null);
	}
	
	public static void error(String msg, Exception e) {
		if (level.ordinal() >= Level.ERROR.ordinal()) {
			print("[E] "+msg);
			if (e != null) {
				e.printStackTrace(System.out);
			}
		}
	}
	
	public static void print(String msg) {
		System.out.println(getTimeSinceStart() + " " + msg);
	}
	
	public static void setLevel(Level level) {
		Logger.level = level;
	}
	
	private static long getTimeSinceStart() {
		return System.currentTimeMillis() - startTime;
	}
}
