package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Item extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private int step = 4;
	private boolean alive = true;
	
	public Item(int x, int y) {
		super(x, y, 20, 20);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);	
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			die();
		}
	}
	
	public boolean isAlive(){
		return alive;
	}

  	public void die(){
  		alive = false;
  	}
}