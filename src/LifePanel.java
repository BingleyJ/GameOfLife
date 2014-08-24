import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JPanel;

public class LifePanel extends JPanel implements Runnable {

	public static int WIDTH = 800;
	public static int HEIGHT = 800;
	public static int CELLSIZE = 10;
	public static int ROWS = HEIGHT / CELLSIZE;
	public static int COLUMNS = WIDTH / CELLSIZE;
	//private int CELLCOUNT = ROWS * COLUMNS;
	public static int STARTINGCELLS = 1000;
	private static int WAITTOSTART = 3;
	
	private int worldsizex = 79;
	private int worldsizey = 79;
	private boolean [][] world  = new boolean [worldsizex][worldsizey];
	private boolean [][] futureworld = new boolean [worldsizex][worldsizey];
	int family = 0;
	
	private Thread thread;
	private Boolean running;
	

	private BufferedImage canvas;
	private Graphics2D crayon;
	private int FPS = 10;
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
	}

	// the thread runsa this
	public void run() {

		running = true;
		canvas = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		crayon = (Graphics2D) canvas.getGraphics();
	
		spawncells();

		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;

		int frameCount = 0;
		int maxFrameCount = 30;

		long targetTime = 1000 / FPS;
		int startcounter = 0;
		// Loop
		while (running) {
			startTime = System.nanoTime();			
			//if (startcounter > WAITTOSTART)	
			//	update();
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
			startcounter++;
		}
	}

	public boolean randomBoolean(){
	    return Math.random() < 0.5;
	}
	
	private void spawncells() {
		//make whole world dedz
		for(boolean[] arr : world){
			Arrays.fill(arr, false);
		}
		for(boolean[] arr : futureworld){
			Arrays.fill(arr, false);
		}
		
		//Glider
		world[1][1] = true;
		world[3][1] = true;
		world[3][2] = true;
		world[2][2] = true;
		world[2][3] = true;
		
		//Lightweight spaceship
		world[9][6] = true;
		world[9][8] = true;
		world[10][10] = true;
		world[11][10] = true;
		world[12][10] = true;
		world[13][10] = true;	
		world[13][9] = true;
		world[13][8] = true;
		world[12][7] = true;
		
		//Blinker 
		world[20][1] = true;
		world[20][2] = true;
		world[20][3] = true;
		
		//Toad
		world[25][3] = true;
		world[26][3] = true;
		world[27][3] = true;
		world[24][4] = true;
		world[25][4] = true;
		world[26][4] = true;
		
		//BEACON
		world[1][20] = true;
		world[2][20] = true;
		world[1][21] = true;
		world[2][21] = true;
		world[3][22] = true;
		world[4][22] = true;
		world[3][23] = true;
		world[4][23] = true;
		
		





	}

	private void chknw(int inX, int inY){ 
		if (world[inX - 1][inY - 1])
			family++;
	}
	private void chkn(int inX, int inY){ 
		if (world[inX][inY - 1])
			family++;
	}
	private void chkne(int inX, int inY){ 
		if (world[inX + 1][inY - 1])
			family++;
	}
	private void chke(int inX, int inY){ 
		if (world[inX + 1][inY])
			family++;
	}
	private void chkw(int inX, int inY){ 
		if (world[inX - 1][inY])
			family++;
	}
	private void chksw(int inX, int inY){ 
		if (world[inX - 1][inY + 1])
			family++;
	}
	private void chks(int inX, int inY){ 
		if (world[inX][inY + 1])
			family++;
	}
	private void chkse(int inX, int inY){ 
		if (world[inX + 1][inY + 1])
			family++;
	}
	
	private void update() {
		for (int i = 0; i < worldsizex; i++) {
			for (int j = 0; j < worldsizey; j++) {
				family = 0;
				//TOP LEFY CORNER OF ARRAY
				if (i == 0 && j == 0){
					chks(i,j);
					chke(i,j);
					chkse(i,j);
				}
				//TOP RIGHT CORNER OF ARRAY
				else if (i == worldsizex - 1 && j == 0){
					chkw(i,j);
					chksw(i,j);
					chks(i,j);
				}
				//BOTTOM LEFT
				else if (i == 0 && j == worldsizey -1){
					chkn(i,j);
					chkne(i,j);
					chke(i,j);
				}
				//BOTTOM RIGHT
				else if (i== worldsizex - 1 && j == worldsizey - 1){
					chkw(i,j);
					chkn(i,j);
					chknw(i,j);
				}
				//TOP ROW
				else if (j == 0 && i > 0){
					chksw(i,j);
					chks(i,j);
					chkse(i,j);
				}
				//BOTTOM ROW
				else if (j == worldsizey - 1){
					chkn(i,j);
					chkne(i,j);
					chknw(i,j);
				}
				//LEFT ROW
				else if (i == 0){
					chkne(i,j);
					chke(i,j);
					chkse(i,j);
				}
				//RIGHT ROW
				else if (i == worldsizex - 1){
					chkw(i,j);
					chknw(i,j);
					chksw(i,j);
				}
				//MIDDLE GRID
				else{
					chknw(i,j);
					chkn(i,j);
					chkne(i,j);
					chkw(i,j);
					chke(i,j);
					chksw(i,j);
					chks(i,j);
					chkse(i,j);	
				}
				
				//IF CURRENT CELL IS ALIVE
				if (world[i][j]){
					if (family == 2 || family == 3){
						futureworld[i][j] = true;
					}
				}
				//IF CURRENT CELL IS DEAD
				if (!world[i][j]){
					if (family == 3){
						futureworld[i][j] = true;
					}
				}
				
				
			}
		}
		//COPY FUTURE TO PRESENT WORLD
		for (int i = 0; i < worldsizex - 1; i++){
			for (int j = 0; j < worldsizex - 1; j++){
				world[i][j] = futureworld[i][j];
			}
		}
		flushfutureworld();
	}

	private void flushfutureworld() {
		for(boolean[] arr : futureworld){
			Arrays.fill(arr, false);
		}
	}

	// DRAW TO BUFFER
	private void render() {
		// draw white background
		Color cl = Color.WHITE;
		crayon.setColor(cl);
		crayon.fillRect(0, 0, WIDTH, HEIGHT);
		
		for (int i = 0; i < worldsizex; i++) {
			for (int j = 0; j < worldsizey; j++) {
				if (world[i][j] == true) {
					crayon.setColor(Color.BLUE);
					crayon.fillRect(i * CELLSIZE, j * CELLSIZE, CELLSIZE, CELLSIZE); // NEEDS WIDTH AND
				}
				else{
					crayon.setColor(Color.WHITE);
					crayon.fillRect(i * CELLSIZE, j * CELLSIZE, CELLSIZE, CELLSIZE); // NEEDS WIDTH AND
				}
			}
		}

		
	}

	// DRAW BUFFER TO SCREEN
	private void draw() {
		Graphics crayon2 = this.getGraphics();
		crayon2.drawImage(canvas, 0, 0, null);
		crayon2.dispose();
		
	}
	private void pressAnyKeyToContinue()
	 { 
	        System.out.println("Press any key to continue...");
	        try
	        {
	            System.in.read();
	        }  
	        catch(Exception e)
	        {}  
	 }

}
