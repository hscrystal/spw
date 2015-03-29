package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class EnemytoYellow extends Enemy{
	private int score = 100;

	public  EnemytoYellow(int x, int y){
		super(x,y);
	}

	@Override
	public void draw(Graphics2D g){
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, width, height);
	}

	@Override
	public int getScore(){
		return score;
	}
}