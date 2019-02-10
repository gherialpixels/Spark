package com.sabgc.spark;

public class Array {
	private double[] array;
	
	public double[] getArray() {
		return array;
	}
	
	public void setArray(double[] array) {
		this.array = array;
	}
	
	public void printArray() {
		System.out.print('|');
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + '|');
		}
		System.out.println();
	}
}
