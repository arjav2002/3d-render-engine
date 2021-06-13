package com.arjav.threedrenderengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Face {

	private double i = 5;
	private float x, y, z;
	private float width, height;
	private ArrayList<Point3D> points;
	private BufferedImage texture;
	
	public Face(float x, float width, float y, float height, float z, BufferedImage texture) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.texture = texture;
		
	}
	
	public void init() {
		points = new ArrayList<Point3D>();
		for(float y_i = y/z; y_i < (y + height)/z; y_i += 0.00025*z) {
			for(float x_i = x/z; x_i < (x + width)/z; x_i += 0.00025*z) {			// get correesponding colour from the texture
				points.add(new Point3D(x_i, y_i, z, texture.getRGB((int)((x_i - x/z)/width*texture.getWidth()),
																	  (int)((y_i - y/z)/height*texture.getHeight()))));
			}
		}
	}
	
	static int getX(float x, float z) {
		double x1 = x / Math.abs(z);
		int tmp = (int) (x1 * Game.WIDTH/2 + Game.WIDTH / 2);
		return tmp;
	}
	
	static int getY(float y, float z) {
		double y1 = y / Math.abs(z);
		int tmp = (int) (y1 * Game.HEIGHT/2 + Game.HEIGHT / 2);
		return tmp;
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < points.size(); i++) {
			Point3D p = points.get(i);
			float tmpX = p.x, tmpY = p.y, tmpZ = p.z;
			p.x = (float)((tmpX * Math.cos(Math.toRadians(this.i))) - (tmpZ * Math.sin(Math.toRadians(this.i))));
			p.z = (float)((tmpX * Math.sin(Math.toRadians(this.i))) + (tmpZ * Math.cos(Math.toRadians(this.i))));
			g.setColor(new Color(p.rgb));
			g.drawLine(getX(p.x, p.z), getY(p.y, p.z), getX(p.x, p.z), getY(p.y, p.z));
		}
	}
	
	public void tick() {
		
	}

}
