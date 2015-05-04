package f2.spw;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Main {
	public static void main(String[] args){
		JFrame frame = new JFrame("Space War");
		JButton buttonStart = new JButton("Start");
		JButton buttonPause = new JButton("Pause");
		JButton buttonRestart = new JButton("Restart");
		buttonStart.setSize(300,0);
		buttonPause.setSize(300,0);
		buttonRestart.setSize(300,0);
		SpaceShip v = new SpaceShip(180, 550, 30, 50);
		GamePanel gp = new GamePanel();
		final GameEngine engine = new GameEngine(gp, v);
		buttonStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				engine.start();
			}
		});
		buttonPause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				engine.stop();
			}
		});

		buttonRestart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				engine.restart();
			}
		});

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(3,1));
		subPanel.add(buttonStart);
		subPanel.add(buttonPause);
		subPanel.add(buttonRestart);

		buttonStart.setFocusable(false);
		buttonPause.setFocusable(false);
		buttonRestart.setFocusable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 640);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.addKeyListener(engine);
		frame.getContentPane().add(gp, BorderLayout.CENTER);
		frame.getContentPane().add(subPanel, BorderLayout.EAST);
		frame.setVisible(true);
	}
}
