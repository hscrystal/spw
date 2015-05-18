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
	private ArrayList<Shield> shields = new ArrayList<Shield>();
	private SpaceShip v;
	private ModifyScore s = new ModifyScore();
	
	private Timer timer;
	
	private long score = 0;
	private int level;
	private long highScore = Long.parseLong(s.getScore());
	private	int upLeval = 500;
	private double difficulty = 0.05;
	private boolean enableItem = true;
	private Boolean shield_active = false;
	private long shield_duration = 0;	
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;
		this.level = 1;

		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		gp.updateGameUI2(this);
	}
	
	public void start(){
		timer.start();
	}

	public void stop(){
		timer.stop();
	}

	public void restart(){
		timer.stop();
		for(Enemy e : enemies) {
			e.die();
		}
		for(Item it : items) {
			it.die();
		}
		for(Bullet b : bullets) {
			b.die();
		}
		for(Shield s : shields) {
			s.die();
		}
		v.resetHeart();
		level = 1;
		score = 0;
		v.resetPosition();
		timer.restart();
	}

	private void process(){
		generateEnemy();
		generateItem();
		moveEnemy();
		moveItem();
		moveBullet();
		moveShield();
		// time();
		
		gp.updateGameUI(this);
		// System.out.println(enableItem);

		
		checkLevel();
		shieldHit();
		bulletHit();
		itemHit();
		shipHit();
	}

	// public void time(){
	// 	counter++;
	// 	if(counter == 20){
	// 		counter = 0;
	// 		sec ++;
	// 		sec %= 60;
	// 	}
	// }

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
		if(Math.random() < 0.1 && enableItem == true){
			if(Math.random()*1 > 0.5){
				if(!shield_active){
					generateItemArmor();
					shield_duration = System.currentTimeMillis() + 20000;
					shield_active = true;
					enableItem = false;
				}
			}
			else{
				generateItemHeart();
				enableItem = false;
			}
		}
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

	private void generateShield(){
		Shield sh = new Shield(v);
		gp.sprites.add(sh);
		shields.add(sh);
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
				enableItem = true;
			}
		}
	}

	public void moveShield(){
		Iterator<Shield> sh_iter = shields.iterator();
		while(sh_iter.hasNext()){
			Shield sh = sh_iter.next();
			sh.proceed(v);
			if(((shield_duration - System.currentTimeMillis()) < 0) && shield_active){
				sh_iter.remove();
				gp.sprites.remove(sh);
				sh.die();
				shield_active = false;
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
				System.out.println("hit");
				if(v.getHeart() == 0){
					if(score > highScore){
						s.setScore(score);
					}
					die();
					return;
				}	
				else {
					e.die();
					v.removeHeart();
				}
			}
		}
	}

	public void shieldHit(){
		Rectangle2D.Double er;
		Rectangle2D.Double sr;
		for(Enemy e : enemies){
			er = e.getRectangle();
			for (Shield sh : shields ) {
				sr = sh.getRectangle();
				if(er.intersects(sr)){
					e.die();
				}		
			}
		}
	}

	public void bulletHit(){
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
				if(it instanceof ItemHeart){
					if(getHeart() < 5){
						v.addHeart();
					}
					it.die();
				}
				if(it instanceof ItemArmor){
					generateShield();
					it.die();
				}
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
		return v.getHeart();
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
