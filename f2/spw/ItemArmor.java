package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class ItemArmor extends Item {
	private Image image = null;

	public ItemArmor(int x, int y){
		super(x,y);
		try{
			image = ImageIO.read(new File("./f2/spw/image/Armor.png"));
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g){
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}
		g.drawImage(image, x, y,width, height,null);
	}

}