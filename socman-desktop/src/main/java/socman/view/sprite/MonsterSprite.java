package socman.view.sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import socman.model.gameobject.GameObject;
import socman.model.gameobject.Monster;

public class MonsterSprite extends Sprite {

	private Color color;
	private final Monster monster;
	
	public MonsterSprite(GameObject monster, int viewX, int viewY) {
		super(viewX, viewY);
		this.monster = ((Monster)monster);
		
		Random rnd = new Random();
		color = new Color(125+rnd.nextInt(125), 125+rnd.nextInt(125), 125+rnd.nextInt(125));
	}
	
	public void update(long timePassed) {
		super.update(timePassed);
	}
	
	@Override public void draw(int scale, Graphics2D g2d) {
		super.draw(scale, g2d);
		final int offset = 2;
		final int side = scale - 4;
		
		g2d.setColor(color);
    	g2d.fillRect(intX()+offset, intY()+offset, side, side);
    	
    	g2d.setColor(color.darker());
    	g2d.drawRect(intX()+offset, intY()+offset, side, side);
    	
    	g2d.setColor(Color.BLACK);
    	
    	// You wouldn't normally want to do this in a tight game loop.
    	String info = monster.getSpeed() + "/" + monster.getMovementDirection().toString().substring(0, 1);
    	g2d.drawString(info, intX()+offset+3, intY()+offset+side/2);
	}
	
}
