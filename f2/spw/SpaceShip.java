package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import java.io.File;

public class SpaceShip extends Sprite{

	int step = 8;
	Image image = null;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		try{
			File url = new File("/home/server/study/OOP/spw/f2/spw/image/shipPng2.png");
			image = ImageIO.read(url);
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
