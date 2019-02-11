package com.sabgc.spark;

public class Vector extends Array {
	
	private double norm;

	public Vector(double x, double y) {
		double[] vect = new double[2];
		vect[0] = x;
		vect[1] = y;
		norm = Math.sqrt(x * x + y * y);
		setArray(vect);
	}
	
	public static Vector add(Vector u, Vector v) {
		double[] uVect = u.getArray();
		double[] vVect = v.getArray();
		
		return new Vector(uVect[0] + vVect[0], uVect[1] + vVect[1]);
	}
	
	public static Vector mult(double a, Vector u) {
		double[] uVect = u.getArray();
		return new Vector(a * uVect[0], a * uVect[1]);
	}
	
	public Vector getUnitVector() {
		double[] vect = getArray();
		return new Vector(vect[0] / norm, vect[1] / norm);
	}
	
	public double getX() {
		return this.getArray()[0];
	}
	
	public double getY() {
		return this.getArray()[1];
	}
	
	public double getNorm() {
		return norm;
	}
	
}
