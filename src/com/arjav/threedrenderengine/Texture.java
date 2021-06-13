package com.arjav.threedrenderengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Texture {
	
	private BufferedImage img;
	private  ArrayList<Point2D> points;
	
	public Texture(BufferedImage img) {
		this.img = img;
		points = new ArrayList<Point2D>();
		
		for(float v = 0; v < img.getHeight(); v++) {
			for(float u = 0; u < img.getWidth(); u++) {
				points.add(new Point2D(u/img.getWidth(), v/img.getHeight(), img.getRGB((int)u, (int)v)));
			}
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < points.size(); i++) {
			Point2D p = points.get(i);
			System.out.println(p.x==1?p.x:"");
			g.setColor(new Color(p.rgb));
			g.drawLine((int)(p.x*img.getWidth()), (int)(p.y*img.getHeight()), (int)(p.x*img.getWidth()), (int)(p.y*img.getHeight()));
		}
	}

}