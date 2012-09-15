package socman.view.sprite;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import socman.model.gameobject.GameObject;
import socman.model.gameobject.Monster;
import socman.model.gameobject.Pill;
import socman.model.gameobject.Socman;

/**
 * Creates Sprites based on game objects from the game model
 */
public class SpriteFactory {

	private static Map<Class<? extends GameObject>, Class<? extends Sprite>> gameObClassToSpriteClass = new HashMap<Class<? extends GameObject>, Class<? extends Sprite>>();
	
	static {
		gameObClassToSpriteClass.put(Socman.class, SocmanSprite.class);
		gameObClassToSpriteClass.put(Monster.class, MonsterSprite.class);
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
			Constructor<?> constructor = getSpriteConstructor(spriteClazz, GameObject.class, int.class, int.class);
			
			if (constructor != null) {
				return (Sprite)constructor.newInstance(gameOb, gameOb.getX() * scale, gameOb.getY() * scale);	
			}
			
			constructor = getSpriteConstructor(spriteClazz, int.class, int.class);	
			return (Sprite)constructor.newInstance(gameOb.getX() * scale, gameOb.getY() * scale);		
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Constructor<?> getSpriteConstructor(Class<?> spriteClazz, Class<?>... params) {

		try {
			return spriteClazz.getConstructor(params);
		} catch (Exception e) {
			return null;
		}
	}

}
