package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy extends Sprite{
	public static final int Y_TO_DIE = 600;
	public int score;
	
	private int step = 4;
	private boolean alive = true;
	
	public Enemy(int x, int y,int score) {
		super(x, y, 20, 20);
		this.score = score;
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

	public int getScore(){
		return score;
	}
}