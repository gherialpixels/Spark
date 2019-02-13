package com.sabgc.spark;

import java.awt.*;
import java.util.Random;

public class ChargeDistribution {

	private double chargeDensity;
	private Surface[] distribution;
	private Vector[][] field = new Vector[40][40];
	private int divisions = 50;
	
	public ChargeDistribution(Surface[] distribution, double chargeDensity) {
		this.distribution = distribution;
		this.chargeDensity = chargeDensity;
		
		Vector E = new Vector(0, 0);
		for (int x = 0; x < 400; x += 10) {
			for (int y = 0; y < 400; y += 10) {
				for (int i = 0; i < distribution.length; i++) {	
					double width = distribution[i].getSideLength();
					for (int j = 0; j < divisions; j++) {
						for (int k = 0; k < divisions; k++) {
							Vector delta = new Vector((j * width) / divisions, (k * width) / divisions);
							Vector r = Vector.add(new Vector(x, y), Vector.mult(-1, Vector.add(distribution[i], delta)));
							if (r.getNorm() != 0)
								E = Vector.add(E, Vector.mult(chargeDensity / (r.getNorm() * r.getNorm()), r.getUnitVector()));
						}
					}
				}
				field[x/10][y/10] = Vector.mult(5, E.getUnitVector());
			}
		}
	}
	
	public Vector getElectricField(Charge Q) {
		// we position the surface with respect to it's top corner.
		Vector E = new Vector(0, 0);
		Vector r = new Vector(0, 0);
		Vector delta = new Vector(0, 0);
		double width;
		
		for (int i = 0; i < distribution.length; i++) {	
			width = distribution[i].getSideLength();
			for (int j = 0; j < divisions; j++) {
				for (int k = 0; k < divisions; k++) {
					delta = new Vector((j * width) / divisions, (k * width) / divisions);
					r = Vector.add(Q.getPos(), Vector.mult(-1, Vector.add(distribution[i], delta)));
					if (r.getNorm() != 0)
						E = Vector.add(E, Vector.mult(chargeDensity / (r.getNorm() * r.getNorm()), r.getUnitVector()));
				}
			}
		}
		
		return Vector.mult(Force.EPSILON_CONST, E);
	}
	
	public static ChargeDistribution getRandomChargeDistribution(double chargeDensity) {
		int x, y;
		int width = 10;
		int length = 20;
		Surface[] S = new Surface[length];
		Random rand = new Random();
		
		x = rand.nextInt(400);
		y = rand.nextInt(400);
		
		int deltaX = rand.nextInt(10);
		int deltaY = rand.nextInt(10);
		S[0] = new Surface(x, y, width);
		
		for (int i = 1; i < length; i++) {
			deltaX = rand.nextInt(10);
			deltaY = rand.nextInt(10);
			if (x + deltaX >= 400 || y + deltaY >= 360) {
				deltaX *= -1;
				deltaY *= -1;
			}
			
			S[i] = new Surface(x + deltaX, y + deltaY, width);
			x += deltaX;
			y += deltaY;
		}
		
		return new ChargeDistribution(S, chargeDensity);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.GRAY);
		double width = 0;
		for (int i = 0; i < distribution.length; i++) {
			width = distribution[i].getSideLength(); 
			g.fillRect((int) distribution[i].getX(), (int) distribution[i].getY(), (int) width, (int) width);
		}
	}
	
	public void paintFieldLines(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < 40; i++) {
			for (int j = 0; j < 40; j++)
			if (field[i] != null)
				g.drawLine((int)(i * 10 + field[i][j].getX()), (int)(j * 10 + field[i][j].getY()), (int)(i * 10 - field[i][j].getX()), (int)(j * 10 - field[i][j].getY()));
		}
	}
}
