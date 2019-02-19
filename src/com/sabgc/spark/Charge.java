package com.sabgc.spark;

import java.awt.*;
import java.util.Random;

public class Charge {

	private double charge;
	private double mass;
	private double radius;
	
	private boolean moving;
	
	private Vector pos;
	private Vector vel;
	private Vector acc;
	
	int r, gr, b;
	
	public Charge(double mass, double charge, int x, int y, int vx, int vy, int radius, boolean moving) {
		pos = new Vector(x, y);
		vel = new Vector(vx, vy);
		acc = new Vector(0, 0);
		this.radius = radius;
		this.moving = moving;
		this.charge = charge;
		this.mass = mass;
		
		Random rand = new Random();
		r = rand.nextInt(256);
		gr = rand.nextInt(256);
		b = rand.nextInt(256);
	}
	
	public void applyForcePoint(Charge[] q) {
		Vector F = new Vector(0, 0);
		acc = Vector.add(acc, Vector.mult(-1, acc));
		if (q.length != 0) {
			F = Vector.add(F, Force.getElectroStaticForcePoint(this, q[0]));
			for (int i = 1; i < q.length; i++) {
				F = Vector.add(F, Force.getElectroStaticForcePoint(this, q[i]));
			}
		}
		acc = Vector.mult((1/mass), F);
	}
	
	public void applyForceDistribution(ChargeDistribution sigma) {
		Vector F = Force.getElectroStaticForceDistribution(this, sigma);
		acc = Vector.add(acc, Vector.mult(1/mass, F));
	}
	
	public void applyGravity() {
		acc = Vector.add(acc, Vector.mult(1/mass, Force.getGravityForce(this)));
	}
	
	public void applyFriction(double a) {
		acc = Vector.add(acc, Vector.mult(1/mass, Force.getFriction(this, a)));
	}
	
	public void update(double width, double height) {
		if (moving) {
			vel = Vector.add(vel, acc);
			pos = Vector.add(pos, vel);
			Force.wallCollision(this, width, height);
		}
	}
	
	public void paint(Graphics g) {
		g.setColor(new Color(r, gr, b));
		g.fillOval((int) (pos.getX() - radius), (int) (pos.getY() - radius), (int) (2 * radius), (int) (2 * radius));
	}
	
	public double getCharge() {
		return charge;
	}
	
	public double getRadius() {
		return radius;
	}

	public Vector getPos() {
		return pos;
	}
	
	public Vector getVel() {
		return vel;
	}
	
	public Vector getAcc() {
		return acc;
	}
	
	public void setPos(double pos0, double pos1) {
		double[] vect = new double[2];
		vect[0] = pos0;
		vect[1] = pos1;
		this.pos.setArray(vect);;
	}
	
	public void setVel(double vel0, double vel1) {
		double[] vect = new double[2];
		vect[0] = vel0;
		vect[1] = vel1;
		this.vel.setArray(vect);;
	}
	
	public void setAcc(double acc0, double acc1) {
		double[] vect = new double[2];
		vect[0] = acc0;
		vect[1] = acc1;
		this.acc.setArray(vect);;
	}
	
}
