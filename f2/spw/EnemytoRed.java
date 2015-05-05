package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class EnemytoRed extends Enemy{
	Image image = null;

	public  EnemytoRed(int x, int y){
		super(x,y,5);
		try{
			image = ImageIO.read(new File("./f2/spw/image/alien1.png"));
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g){
		g.drawImage(image, x, y,width, height,null);
	}

}