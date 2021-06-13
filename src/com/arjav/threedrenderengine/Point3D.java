package com.arjav.threedrenderengine;

public class Point3D {
	
	float x, y, z;
	int rgb;
	
	public Point3D(float x, float y, float z, int rgb) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.rgb = rgb;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setZ(float z) {
		this.z = z;
	}

}
