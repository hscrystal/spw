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
	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.1;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
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

	private void process(){
		if(Math.random() < difficulty){
			if(Math.random()*1 > 0.5)
				generateEnemyRed();
			else
				generateEnemyYellow();
		}

		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += e.getScore();
			}
		}

		Iterator<Bullet> b_iter = bullets.iterator();
		while(b_iter.hasNext()){
			Bullet b = b_iter.next();
			b.proceed();
			
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D vr = v.getRectangle();
		Rectangle2D er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				die();
				return;
			}
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
