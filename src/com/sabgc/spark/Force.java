package com.sabgc.spark;

import java.awt.*;

public class Force {
	
	// static final double EPSILON_CONST = 1;
	public static final double EPSILON_CONST = 8988000000.00;
	
	public static Vector getElectroStaticForcePoint(Charge q1, Charge q2) {
		Vector r = Vector.add(q1.getPos(), Vector.mult(-1, q2.getPos()));
		Vector F = Vector.mult(EPSILON_CONST * q1.getCharge() * q2.getCharge() / 
				   (r.getNorm() * r.getNorm()), r.getUnitVector());
		return F;
	}
	
	public static Vector getElectroStaticForceDistribution(Charge Q, ChargeDistribution sigma) {
		Vector F = Vector.mult(Q.getCharge(), sigma.getElectricField(Q));
		return F;
	}
	
	public static Vector getGravityForce(Charge Q) {
		Vector F = new Vector(0, 9.81);
		return F;
	}
	
	public static Vector getFriction(Charge Q, double a) {
		Vector F = Vector.mult(-a, Q.getVel());
		return F;
	}
	
	public static void wallCollision(Charge q1, double width, double height) {
		double[] vel = q1.getVel().getArray();
		double[] pos = q1.getPos().getArray();
		double radius = q1.getRadius();
		
		double x = pos[0];
		double y = pos[1];
		
		double vx = vel[0];
		double vy = vel[1];
		
		if (x + radius > width) {
			vx *= -1;
			x = width - radius;
		} else if (x - radius < 0) {
			vx *= -1;
			x = radius;
		}
		
		if (y + radius > height) {
			vy *= -1;
			y = height - radius;
		} else if (y - radius < 0) {
			vy *= -1;
			y = radius;
		}
		
		q1.setPos(x, y);
		q1.setVel(vx, vy);
	}
}
