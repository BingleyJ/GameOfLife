import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class LifePanel extends JPanel implements Runnable {

	public static int WIDTH = 200;
	public static int HEIGHT = 200;
	public static int CELLSIZE = 10;
	public static int ROWS = HEIGHT / CELLSIZE;
	public static int COLUMNS = WIDTH / CELLSIZE;
	private int CELLCOUNT = ROWS * COLUMNS;
	public static int STARTINGCELLS = 1000;

	private Thread thread;
	private Boolean running;
	boolean ready = false;

	private BufferedImage canvas;
	private Graphics2D crayon;
	private int FPS = 1;
	private double averageFPS;

	public static ArrayList<Cell> cells;

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
	}

	// the thread runsa this
	public void run() {

		running = true;
		canvas = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		crayon = (Graphics2D) canvas.getGraphics();

		cells = new ArrayList<Cell>();
		// initial setup
		// spawncells(cellCount);
		// for (int i = 0 ; i < 600; i++){
		// cells.add(new Cell(i, 10));
		// }
		//cells.add(new Cell(10, 10));
		//cells.add(new Cell(10, 0));
		//cells.add(new Cell(10, 0));

		//cells.add(new Cell(0, 0));
		//cells.add(new Cell(0, 10));
		//cells.add(new Cell(10, 20));

		//cells.add(new Cell(200, 200));
		spawncells();

		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;

		int frameCount = 0;
		int maxFrameCount = 30;

		long targetTime = 1000 / FPS;

		// Loop
		while (running) {

			startTime = System.nanoTime();

			if (ready)
				update();
			render();
			draw();

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

		}
	}

	public boolean randomBoolean(){
	    return Math.random() < 0.5;
	}
	
	private void spawncells() {
		Random random = new Random();
		for (int i = 0; i < WIDTH; i += 10) {
			for(int j = 0; j < HEIGHT; j += 10){
				if (randomBoolean())
					cells.add(new Cell(i, j));
			}

		}

	}

	private void update() {

		for (int i = 0; i < cells.size(); i++) {
			int children = 0;
			Cell currentCell = cells.get(i);
			//System.out.println("Current Cell # ="
			//		+ currentCell.getWorldPosition());

			// System.out.println("ALIVE");
			for (int j = 0; j < cells.size(); j++) {
				// System.out.println("second for");

				Cell tempcell = cells.get(j);
				
				// NE
				if (!tempcell.isDead()) {
					if (tempcell.getWorldPosition() == currentCell
							.getWorldPosition() - 39) {
						System.out.println("cell NORTHEAST");
						children++;
					}
					// N
					if (tempcell.getWorldPosition() == currentCell
							.getWorldPosition() - 40) {
						System.out.println("cell NORTH");
						children++;
					}
					// NW
					if (tempcell.getWorldPosition() == currentCell
							.getWorldPosition() - 41) {
						System.out.println("cell NORTHWEST");
						children++;
					}
					// W
					if (tempcell.getWorldPosition() == currentCell
							.getWorldPosition() - 1) {
						System.out.println("cell WEST");
						children++;
					}
					// E
					if (tempcell.getWorldPosition() == currentCell
							.getWorldPosition() + 1) {
						System.out.println("cell WEST");
						children++;
					}
					// SW
					if (tempcell.getWorldPosition() == currentCell
							.getWorldPosition() + 39) {
						System.out.println("cell SOUTHWEST");
						children++;
					}
					// S
					if (tempcell.getWorldPosition() == currentCell
							.getWorldPosition() + 40) {
						System.out.println("cell SOUTHWEST");
						children++;
					}
					// SE
					if (tempcell.getWorldPosition() == currentCell
							.getWorldPosition() + 41) {
						System.out.println("cell SOUTHEAST");
						children++;
					}
				}
			}

			if (!currentCell.isDead()) {
				if (children < 2) {
					currentCell.kill();
					//System.out.println("KILLED Children");

				} else if (children > 3) {
					currentCell.kill();
					//System.out.println("KILLED Children");
				} else {
				}
			}
			if (currentCell.isDead()) {
				if (children == 3) {
					currentCell.unkill();
				}
			}
		}
	}

	// DRAW TO BUFFER
	private void render() {

		// draw white background
		Color cl = Color.WHITE;
		cl.brighter();
		cl.brighter();
		cl.brighter();
		cl.brighter();
		crayon.setColor(cl);
		crayon.fillRect(0, 0, WIDTH, HEIGHT);

		// draw fps
		crayon.setColor(Color.BLUE);
		crayon.drawString("FPS: " + averageFPS, 10, 100);

		// draw cells
		for (int i = 0; i < cells.size(); i++) {
			cells.get(i).draw(crayon);
		}
	}

	// DRAW BUFFER TO SCREEN
	private void draw() {
		ready = true;
		Graphics crayon2 = this.getGraphics();
		crayon2.drawImage(canvas, 0, 0, null);
		crayon2.dispose();
	}

	private void pause() {
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
