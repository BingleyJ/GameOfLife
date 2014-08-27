import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LifePanel extends JPanel implements Runnable, KeyListener {

	public static int WIDTH = 800;
	public static int HEIGHT = 800;
	
	public static int HELPSCREENLOCATIONX = 280;
	public static int HELPSCREENLOCATIONY = 280;
	
	private World world = new World(WIDTH, HEIGHT);
	private Helper helper = new Helper();
	private PopUps popups = new PopUps();
	
	private Thread thread;
	private Boolean running;
	private boolean keyDown = false;
	
	private BufferedImage canvas;
	private Graphics2D crayon;
	public int FPS = 10;
	private double averageFPS;

	public LifePanel() {
		// initialize my parent class before you initialize me
		super();
		// The Dimension class encapsulates the width and height of a component
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		// make me active
		requestFocus();
	}

	// This is an override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
	}

	// the thread runs this
	public void run() {
		running = true;
		canvas = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		crayon = (Graphics2D) canvas.getGraphics();
		//FPS STUFF
		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;
		int frameCount = 0;
		int maxFrameCount = 30;
		long targetTime = 1000 / FPS;
	
		//GAMELOOP--------------------------------------]
		popups.drawWelcome();
		while (running) {
			startTime = System.nanoTime();			
			render();
			draw();
			update();
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - URDTimeMillis;
			try {
				Thread.sleep(waitTime);
			} catch (Exception e) {
			}

			totalTime += System.nanoTime() - startTime;
			frameCount++;
			if (frameCount == maxFrameCount) {
				averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
		}//GAMELOOP END--------------------------------------]
	}

		
	private void update() {
		world.update();
	}

	// DRAW TO BUFFER
	private void render() {
		//background
		Color cl = Color.WHITE;
		crayon.setColor(cl);
		crayon.fillRect(0, 0, WIDTH, HEIGHT);
		//DRAW LIVE CELLS TO BOARD
		world.Draw(crayon);
	}

	// DRAW BUFFER TO SCREEN
	private void draw() {
		Graphics crayon2 = this.getGraphics();
		crayon2.drawImage(canvas, 0, 0, null);
		crayon2.dispose();
	}
	
	public void keyPressed(KeyEvent key) {
		//AVOID EXCEPTION FAULT FROM SPAMMIUNG KEYDOWN
		if (!keyDown) {
			switch (key.getKeyCode()) {
			case KeyEvent.VK_0:
				world.randomWorld();
				break;
			case KeyEvent.VK_1:
				world.spawnGlider();
				break;
			case KeyEvent.VK_2:
				world.spawnLWS();
				break;
			case KeyEvent.VK_3:
				world.spawnBlinker();
				break;
			case KeyEvent.VK_4:
				world.spawnToad();
				break;
			case KeyEvent.VK_5:
				world.spawnBeacon();
				break;
			case KeyEvent.VK_T:
				world.setTrippymode(true);
				break;
			case KeyEvent.VK_Y:
				world.setTrippymode(false);
				break;
			case KeyEvent.VK_SPACE:
				world.spawncells();
				break;
			case KeyEvent.VK_COMMA:
				FPS++;
				break;
			case KeyEvent.VK_H:
				popups.drawHelp();
				break;
			case KeyEvent.VK_R:
				popups.drawRules();
				break;
			}
		}
		keyDown = true;
		}
	
	public void keyReleased(KeyEvent e) {
		keyDown = false;
	}

	public void keyTyped(KeyEvent e) {
	}

}
