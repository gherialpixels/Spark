package com.sabgc.spark;

import java.awt.*;
import javax.swing.*;

public class Game extends JPanel {
	
	private int width, height;
	
	private int x1 = 100;
	private int y1 = 100;
	
	private int x2 = 385;
	private int y2 = 15;
	
	private int x3 = 15;
	private int y3 = 370;
	
	private int vx = 4;
	private int vy = -4;
	
	private int radius = 15;
	
	private int frames = 0;
	private double now = 0;
	
	public Game() {
		Charge Q = new Charge(100000, 0.25, x1, y1, vx, vy, radius, true);
		Charge q0 = new Charge(10, 0.125, x2, y2, 0, 0, radius, false);
		Charge q1 = new Charge(10, 0.125, x3, y3, 0, 0, radius, false);
		Charge[] qs = {q0, q1};
		
		Thread thread = new Thread() {
			public void run() {
				double start = System.currentTimeMillis();
				while (true) {
					width = getWidth();
					height = getHeight();
					
					Q.applyForce(qs);
					Q.update(width, height, qs);
					
					repaint();
					
					try {
						Thread.sleep(15);
					} catch (InterruptedException e) {}	
					
					frames++;
					now = System.currentTimeMillis();
					if (now - start >= 1000) {
						System.out.println("Frames: " + frames);
						frames = 0;
						start = System.currentTimeMillis();
					}
					
					x1 = (int) Q.getPos().getArray()[0];
					y1 = (int) Q.getPos().getArray()[1];
				}
			}
		};
		thread.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.CYAN);
		g.fillOval((int)(x1 - radius), (int)(y1 - radius), (int)(2 * radius), (int)(2 * radius));
		g.setColor(Color.GREEN);
		g.fillOval((int)(x2 - radius), (int)(y2 - radius), (int)(2 * radius), (int)(2 * radius));
		g.setColor(Color.BLUE);
		g.fillOval(x3-radius, y3-radius, 2 * radius, 2 * radius);
	}
	
	public static void main(String [] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Spark");
		frame.setSize(400, 400);
		frame.setContentPane(new Game());
		frame.setVisible(true);
		
	}

}
