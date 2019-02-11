package com.sabgc.spark;

public class Surface extends Vector {

	// pos represents the centre of the surface
	private double sideLength;
	
	public Surface(double x, double y, double sideLength) {
		// Surface vector = [posX, posY, sideLength]. In the future,
		// Maybe Surface vector = [(posX, posY), (sideLength)] would be better.
		super(x, y);
		this.sideLength = sideLength;
	}
	
	public double getSideLength() {
		return sideLength;
	}
	
}
