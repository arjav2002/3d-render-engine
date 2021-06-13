package com.arjav.threedrenderengine;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class Tester extends JFrame{
	
	final int xPos = 100, yPos = 100;
	int i = 1;

	Tester(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		while(true) {
			g.clearRect(0, 0, getWidth(), getHeight());
			int x = (int) (xPos * Math.sin(Math.toRadians(i)));
			int y = (int) (yPos * Math.cos(Math.toRadians(i)));
			g.setColor(Color.pink);
			g.fillRect(x, y, 50, 50);
		}
	}
	
	public static void main(String[] args) {
		new Tester("Tester", 500, 500);

	}

}
