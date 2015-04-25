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

	public Shield(SpaceShip s){
		super(s.x+((s.width/2)-(80/2)), s.y, 80, 80);
		this.s = s;
		try{
			shield = ImageIO.read(new File("./f2/spw/image/crescent.png"));
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, x, y,width, height,null);	
	}

	public void proceed(){
		y -= step;
		if(y < Y_TO_DIE){
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