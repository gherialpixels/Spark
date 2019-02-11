package com.sabgc.spark;

import java.awt.*;
import javax.swing.*;

public class Game extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private int width, height;
	
	private int radius = 15;
	
	private Charge Q = new Charge(100000, 0.25, 100, 100, 4, -4, radius, true);
	/*
	private Charge q0 = new Charge(10, 0.25, 385, 15, 0, 0, radius, false);
	private Charge q1 = new Charge(10, 0.25, 15, 365, 0, 0, radius, false);
	private Charge[] qs = {q0, q1};
	*/
	
	private ChargeDistribution sigma = ChargeDistribution.getRandomChargeDistribution(0.0001);
	
	private int frames = 0;
	private double now = 0;
	
	public Game() {
		Thread thread = new Thread() {
			public void run() {
				double start = System.currentTimeMillis();
				while (true) {
					width = getWidth();
					height = getHeight();
					
					
					// Q.applyForcePoint(qs);
					Q.applyForceDistribution(sigma);
					Q.update(width, height);
					
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
				}
			}
		};
		thread.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Q.paint(g);
		sigma.paint(g);
		/*
		q0.paint(g);
		q1.paint(g);
		*/
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
