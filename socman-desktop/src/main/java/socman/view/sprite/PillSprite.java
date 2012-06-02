package socman.view.sprite;

import java.awt.Color;
import java.awt.Graphics2D;

public class PillSprite extends Sprite {

	private static final Color color = Color.pink;
	private static final Color bgColor = color.darker();

	public PillSprite(int viewX, int viewY) {
		super(viewX, viewY);
	}
	
	public void update(long timePassed) {
		super.update(timePassed);
	}
	
	@Override public void draw(int scale, Graphics2D g2d) {
		super.draw(scale, g2d);
		int radius = scale/6; 
		int diameter = radius*2;
		int offset = (scale - diameter) / 2;
	
		g2d.setColor(color);
    	g2d.fillOval(Math.round(x)+offset, Math.round(y)+offset, diameter, diameter);
    	g2d.setColor(bgColor);
    	g2d.drawOval(Math.round(x)+offset, Math.round(y)+offset, diameter, diameter);
	}

}
