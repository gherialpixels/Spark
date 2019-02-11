package com.sabgc.spark;

import java.awt.*;

public class ChargeDistribution {

	private double chargeDensity;
	private Surface[] distribution;
	
	public ChargeDistribution(Surface[] distribution, double chargeDensity) {
		this.distribution = distribution;
		this.chargeDensity = chargeDensity;
	}
	
	public Vector getElectricField(Charge Q) {
		// we position the surface with respect to it's top corner.
		Vector E = new Vector(0, 0);
		Vector r = new Vector(0, 0);
		Vector delta = new Vector(0, 0);
		double width;
		for (int i = 0; i < distribution.length; i++) {	
			width = distribution[i].getSideLength();
			for (int j = 0; j < 20; j++) {
				for (int k = 0; k < 20; k++) {
					delta = new Vector((j * width)/20, (k * width) / 20);
					r = Vector.add(Q.getPos(), Vector.mult(-1, Vector.add(distribution[i], delta)));
					E = Vector.add(E, Vector.mult(chargeDensity / (r.getNorm() * r.getNorm()), r.getUnitVector()));
				}
			}
		}
		
		return Vector.mult(Force.EPSILON_CONST, E);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.GRAY);
		double width = 0;
		for (int i = 0; i < distribution.length; i++) {
			width = distribution[i].getSideLength(); 
			g.fillRect((int) distribution[i].getX(), (int) distribution[i].getY(), (int) width, (int) width);
		}
	}
}
