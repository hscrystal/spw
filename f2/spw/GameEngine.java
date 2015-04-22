package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private SpaceShip v;
	private ModifyScore s = new ModifyScore();
	
	private Timer timer;
	
	private long score = 0;
	private int heart;
	private int level;
	private long highScore = Long.parseLong(s.getScore());
	private	int upLeval = 500;
	private double difficulty = 0.05;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;
		this.heart = 5;
		this.level = 1;

		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}

	public void generateEnemy(){
		if(Math.random() < difficulty){
			if(Math.random()*1 > 0.5)
				generateEnemyRed();
			else
				generateEnemyYellow();
		}
	}
	
	private void generateEnemyRed(){
		EnemytoRed e = new EnemytoRed((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	
	private void generateEnemyYellow(){
		EnemytoYellow e = new EnemytoYellow((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generatebullet(){
		Bullet b = new Bullet(v);
		gp.sprites.add(b);
		bullets.add(b);
	}

	private void generateItem(){
		if(Math.random() < 0.01)
			if(Math.random()*1 > 0.5)
				generateItemArmor();
			else
				generateItemHeart();
	}

	private void generateItemArmor(){
		ItemArmor it = new ItemArmor((int)(Math.random()*390), 30);
		gp.sprites.add(it);
		items.add(it);
	}

	private void generateItemHeart(){
		ItemHeart it = new ItemHeart((int)(Math.random()*390), 30);
		gp.sprites.add(it);
		items.add(it);
	}

	private void process(){
		generateEnemy();
		generateItem();
		moveEnemy();
		moveItem();
		moveBullet();
		
		gp.updateGameUI(this);
		
		checkLevel();
		bulletHit();
		itemHit();
		shipHit();
	}

	public void moveEnemy(){
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
			}
		}
	}

	public void moveItem(){
		Iterator<Item> it_iter = items.iterator();
		while(it_iter.hasNext()){
			Item it = it_iter.next();
			it.proceed();
			
			if(!it.isAlive()){
				it_iter.remove();
				gp.sprites.remove(it);
			}
		}
	}

	public void moveBullet(){
		Iterator<Bullet> b_iter = bullets.iterator();
		while(b_iter.hasNext()){
			Bullet b = b_iter.next();
			b.proceed();
			
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}
	}

	public void shipHit(){
		Rectangle2D vr = v.getRectangle();
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			Rectangle2D er = e.getRectangle();
			if(er.intersects(vr)){
				if(heart == 0){
					if(score > highScore){
						s.setScore(score);
					}
					die();
					return;
				}	
				else {
					e.die();
					heart --;
				}
			}
		}
	}

	public void bulletHit(){
		Iterator<Bullet> b_iter = bullets.iterator();
		Iterator<Enemy> e_iter = enemies.iterator();
		Rectangle2D.Double er;
		Rectangle2D.Double br;
		for(Enemy e : enemies){
			er = e.getRectangle();
			for (Bullet b : bullets ) {
				br = b.getRectangle();
				if(er.intersects(br)){
					score += e.getScore();
					e.die();
					b.die();
				}		
			}
		}
	}

	public void itemHit(){
		Rectangle2D vr = v.getRectangle();
		Iterator<Item> it_iter = items.iterator();
		while(it_iter.hasNext()){
			Item it = it_iter.next();
			Rectangle2D itr = it.getRectangle();
			if(itr.intersects(vr)){
				heart++;
				it.die();
			}
		}
	}

	public void checkLevel(){
		if(score > upLeval){
			upLeval += 500;
			level++;
			difficulty += 0.1;
			System.out.println(level);
		}
	}
	
	public void die(){
		timer.stop();
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.moveHorizontal(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v.moveHorizontal(1);
			break;
		case KeyEvent.VK_UP:
			v.moveVertical(-1);
			break;
		case KeyEvent.VK_DOWN:
			v.moveVertical(1);
			break;
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break;
		case KeyEvent.VK_SPACE:
			generatebullet();
			break;
		}
	}

	public long getScore(){
		return score;
	}

	public int getHeart(){
		return heart;
	}

	public int getLevel(){
		return level;
	}

	public long getHighScore(){
		if(highScore > score){
			return highScore;
		}else {
			return score;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
