package com.arjav.threedrenderengine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame implements Runnable, KeyListener {
	
	Thread t = new Thread(this), renderThread;
	public static final int WIDTH = 720, HEIGHT = 720;
	private static final String TITLE = "My 3D Engine";
	private volatile boolean running = false;
	private boolean initialized = false;
	private Canvas canvas;
	private int frames = 0, fps = 0;
	private BufferStrategy bs;
	private Graphics g;
	private Face f;
	private Texture tex;
	
	public static void main(String[] args) {
		Game game = new Game(TITLE, WIDTH, HEIGHT);
		game.start();

	}
	
	public Game(String title, int width, int height) {
		setSize(width, height);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);		
		addKeyListener(this);
		
		canvas = new Canvas();
		canvas.setPreferredSize(getSize());
		canvas.setMaximumSize(getSize());
		canvas.setMinimumSize(getSize());
		canvas.setFocusable(false);
		add(canvas);
		
		setVisible(true);
		
		renderThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				long lastTime = System.nanoTime(); // getting time correct to nano seconds
		        final double amountOfTicks = 60.0; // number of target frames per second
		        double ns = 1000000000 / amountOfTicks; // variable which contains 1 / target frames per second value
		        double delta = 0; // difference between two times

		        while(running){ // while the game is running, we want to -
		        	if(!initialized)
		        		continue;
		        	long now = System.nanoTime(); // get the time correct to nanoseconds 
		        	delta += (now - lastTime) / ns; // fancy calculation to get the time between last update and now in seconds
		        	lastTime = now; // lastTime will be equal to now for future 
		        	if(delta >= 1){ // if more than or equal to one / (target frames per second)  seconds of time has passed , we have to
		        		render(); // update
		        		frames++; // record it
		               	delta--; // since we have updated already we can decrease delta by one / (target frames per second) that is 1
		        	}
		        }
			}
			
		});
		
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(getClass().getResource("/texture.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		f = new Face(-0.1f, 0.1f, -0.1f, 0.1f, 1f, texture);
	}
	
	private synchronized void start() {
		if(running) return;
		t.start();
		renderThread.start();
		running = true;
	}
	
	private synchronized void stop() {
		if(!running) return;
		try {
			t.join();
			renderThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			break;
		case KeyEvent.VK_LEFT:
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime(); // getting time correct to nano seconds
        final double amountOfTicks = 60.0; // number of target frames per second
        double ns = 1000000000 / amountOfTicks; // variable which contains 1 / target frames per second value
        double delta = 0; // difference between two times
        int updates = 0; // number of updates
        long timer = System.currentTimeMillis(); // helps in printing fps and ticks every second

        while(running){ // while the game is running, we want to -
        	long now = System.nanoTime(); // get the time correct to nanoseconds 
        	delta += (now - lastTime) / ns; // fancy calculation to get the time between last update and now in seconds
        	lastTime = now; // lastTime will be equal to now for future 
        	if(delta >= 1){ // if more than or equal to one / (target frames per second)  seconds of time has passed , we have to
        		tick(); // update
        		updates++; // record it
               	delta--; // since we have updated already we can decrease delta by one / (target frames per second) that is 1
        	}
        		
        	if(System.currentTimeMillis() - timer > 1000){ // if one second has passed
       			timer += 1000;
       			System.out.println(updates + " Ticks, Fps " + frames); // print the updates and frames per seconds on the console
       			updates = 0; // updates 
       			fps = frames; // recording the number of frames rendered last second
       			frames = 0; // and frames to be made 0 for printing the correct set of frames and updates next time
       			
       		}
        }
	}
	
	private void init() {
		f.init();
		initialized = true;
	}
	
	private void render() {		
		if(bs == null) canvas.createBufferStrategy(3);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.drawLine(Game.WIDTH/2, 0, Game.WIDTH/2, Game.HEIGHT);
		g.drawLine(0, Game.HEIGHT/2, Game.WIDTH, Game.HEIGHT/2);
		f.render(g);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Serif", Font.BOLD, 20));
		g.drawString(fps + " Frame(s) rendered last Second", 2, 17);
		
		bs.show();
		g.dispose();
	}
	
	private void tick() {
		f.tick();
	}

}
