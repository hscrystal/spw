package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet extends Sprite{
	public static final int Y_TO_DIE = 0;
	
	private int step = 12;
	private boolean alive = true;
	private SpaceShip s;
	
	public Bullet(SpaceShip s) {
		super(s.x, s.y, 5, 10);
		this.s = s;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect(x+((s.width/2)-(width/2)), y, width, height);
		
	}

	public void proceed(){
		y -= step;
		if(y < Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}

}