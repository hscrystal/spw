package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class Shield extends Sprite {

	private SpaceShip s;
	private Image shield = null;
	private boolean alive = true;

	public Shield(SpaceShip s){
		super(s.x+((s.width/2)-(60/2)), s.y, 60, 60);
		this.s = s;
		try{
			shield = ImageIO.read(new File("./f2/spw/image/crescent.png"));
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(shield, x, y,width, height,null);	
	}

	public void proceed(SpaceShip s){
		x = s.x-15;
		y = s.y-25;
	}
	
	public boolean isAlive(){
		return alive;
	}

	public void die(){
  		alive = false;
  	}
}