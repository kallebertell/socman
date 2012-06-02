package socman.view.sprite;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import socman.model.gameobject.GameObject;
import socman.model.gameobject.Ghost;
import socman.model.gameobject.Socman;
import socman.model.gameobject.Pill;

/**
 * Creates Sprites based on game objects from the game model
 */
public class SpriteFactory {

	private static Map<Class<? extends GameObject>, Class<? extends Sprite>> gameObClassToSpriteClass = new HashMap<Class<? extends GameObject>, Class<? extends Sprite>>();
	
	static {
		gameObClassToSpriteClass.put(Socman.class, SocmanSprite.class);
		gameObClassToSpriteClass.put(Ghost.class, GhostSprite.class);
		gameObClassToSpriteClass.put(Pill.class, PillSprite.class);
	}
	
	/**
	 * 
	 * @param gameOb
	 * @param scale
	 * @return new sprite instance
	 */
	public static Sprite createSprite(GameObject gameOb, int scale) {
		Class<? extends Sprite> spriteClazz = gameObClassToSpriteClass.get(gameOb.getClass());
	
		try {
			Constructor<?> con = spriteClazz.getConstructor(int.class, int.class);
			return (Sprite)con.newInstance(gameOb.getX() * scale, gameOb.getY() * scale);		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
