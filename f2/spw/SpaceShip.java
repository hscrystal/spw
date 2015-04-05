package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import java.io.File;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public class SpaceShip extends Sprite{

	int step = 8;
	Image image = null;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		try{
			image = ImageIO.read(new File("./f2/spw/image/shipPng2.png"));
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		// g.setColor(Color.GREEN);
		// g.fillRect(x, y, width, height);
		g.drawImage(image, x, y,width, height,null);
	}

	@Override
	public Double getRectangle() {
		return new Rectangle2D.Double(x, y, width/2, height);
	}

	public void moveHorizontal(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
	}

	public void moveVertical(int direction){
		y += (step * direction);
		if(y < 0)
			y = 0;
		if(y > 600 - height)
			y = 600 - height;
	}

}
