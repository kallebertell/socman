package socman.view.sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class GhostSprite extends Sprite {

	private Color color;
	
	public GhostSprite(int viewX, int viewY) {
		super(viewX, viewY);
		Random rnd = new Random();
		color = new Color(125+rnd.nextInt(125), 125+rnd.nextInt(125), 125+rnd.nextInt(125));
	}
	
	public void update(long timePassed) {
		super.update(timePassed);
	}
	
	@Override public void draw(int scale, Graphics2D g2d) {
		super.draw(scale, g2d);
		g2d.setColor(color);
    	g2d.fillRect(Math.round(x)+scale/4, Math.round(y)+scale/4, scale/2, scale/2);
    	g2d.setColor(color.darker());
    	g2d.drawRect(Math.round(x)+scale/4, Math.round(y)+scale/4, scale/2, scale/2);
	}

}
