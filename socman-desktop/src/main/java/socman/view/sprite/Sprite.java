package socman.view.sprite;

import java.awt.Graphics2D;

public class Sprite {
	protected float x;
	protected float y;
	
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(int scale, Graphics2D g2d) {
	}


	public void update(long timePassed) {		
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int intX() {
		return Math.round(x);
	}
	
	public int intY() {
		return Math.round(y);
	}
	
	@Override public String toString() {
		return getClass().getSimpleName() + " at ("+getX()+","+getY()+")";
	}
}
