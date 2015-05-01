package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;
	private Image image = null;	
	private Image heart = null;
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		//big.setBackground(Color.BLACK);
		try{
			image = ImageIO.read(new File("./f2/spw/image/bg.jpg"));
			heart = ImageIO.read(new File("./f2/spw/image/heart.png"));
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		big.drawImage(image, 0, 0, null);
		big.setColor(Color.WHITE);
		big.drawString(String.format("High Score : "+"%06d", reporter.getHighScore()), 265, 20);		
		big.drawString(String.format("Score : "+"%06d", reporter.getScore()), 265, 50);
		drawHeart(reporter.getHeart());
		drawLevel(reporter.getLevel());
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	public void updateGameUI2(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		big.drawImage(image, 0, 0, null);
		big.setColor(Color.WHITE);
		big.drawString(String.format("Press S to Strart Game"), 130, 300);		
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}


	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

	public void drawHeart(int numheart){
		big.setColor(Color.WHITE);
		big.drawString(String.format("Heart : "), 5, 20);
		int x = 50;
		for(int i=0; i < numheart ; i++){
			big.drawImage(heart, x , 8, null);
			x += 20;
		}
	}

	public void drawLevel(int level){
		big.setColor(Color.WHITE);
		big.drawString(String.format("Level : "+"%02d",level), 5, 50);
	}
}
