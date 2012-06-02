package socman.view.sprite;

import java.awt.Color;
import java.awt.Graphics2D;

public class SocmanSprite extends Sprite {

	private static final int OFFSET = 5;
	
	private static final Color color = Color.yellow;
	private static final Color bgColor = color.darker();
	
	public SocmanSprite(int viewX, int viewY) {
		super(viewX, viewY);
	}
	
	public void update(long timePassed) {
		super.update(timePassed);
	}
	
	@Override public void draw(int scale, Graphics2D g2d) {
		super.draw(scale, g2d);
		g2d.setColor(color);
    	g2d.fillOval(Math.round(x)+OFFSET, Math.round(y)+OFFSET, scale-OFFSET*2, scale-OFFSET*2);
    	g2d.setColor(bgColor);
    	g2d.drawOval(Math.round(x)+OFFSET, Math.round(y)+OFFSET, scale-OFFSET*2, scale-OFFSET*2);
	}

}
