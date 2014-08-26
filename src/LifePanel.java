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
	public static int CELLSIZE = 10;
	
	public static int HELPSCREENLOCATIONX = 280;
	public static int HELPSCREENLOCATIONY = 280;
	
	private int worldsizex = 80;
	private int worldsizey = 80;
	private boolean [][] world  = new boolean [worldsizex][worldsizey];
	private boolean [][] futureworld = new boolean [worldsizex][worldsizey];
	private int connectedCells = 0;
	
	private boolean trippymode = false;
	
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

	// the thread runsa this
	public void run() {
		running = true;
		canvas = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		crayon = (Graphics2D) canvas.getGraphics();
		// CREATE 2D ARRAYS OF DEAD CELLS
		spawncells();
		//FPS STUFF
		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;
		int frameCount = 0;
		int maxFrameCount = 30;
		long targetTime = 1000 / FPS;
	
		//GAMELOOP--------------------------------------]
		drawWelcome();
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

	public boolean randomBoolean(){
	    return Math.random() < 0.5;
	}
	
	public int randIntInsideWorld() {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((worldsizex - 5) + 1) + 5;
	    return randomNum;
	}
	
	public int randmaxminInt(int max, int min) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	private void spawnGlider(){
		int tempintx = randmaxminInt(worldsizex - 10, 10);
		int tempinty = randmaxminInt(worldsizey - 10, 10);
			//SE BOUND
			world[tempintx][tempinty] = true;
			world[tempintx + 2][tempinty] = true;
			world[tempintx + 2][tempinty + 1] = true;
			world[tempintx + 1][tempinty + 1] = true;
			world[tempintx + 1][tempinty + 2] = true;
	}
	
	private void spawnLWS(){
		int tempintx = randmaxminInt(worldsizex - 10, 10);
		int tempinty = randmaxminInt(worldsizey - 10, 10);
		world[tempintx][tempinty] = true;
		world[tempintx][tempinty + 2] = true;
		world[tempintx + 1][tempinty + 4] = true;
		world[tempintx + 2][tempinty + 4] = true;
		world[tempintx + 3][tempinty + 4] = true;
		world[tempintx + 4][tempinty + 4] = true;	
		world[tempintx + 4][tempinty + 3] = true;
		world[tempintx + 4][tempinty + 2] = true;
		world[tempintx + 3][tempinty + 1] = true;
	}
	
	private void spawnBlinker(){
		int tempintx = randmaxminInt(worldsizex - 10, 10);
		int tempinty = randmaxminInt(worldsizey - 10, 10);
		world[tempintx][tempinty] = true;
		world[tempintx][tempinty + 1] = true;
		world[tempintx][tempinty + 2] = true;
	}
	
	private void spawnToad(){
		int tempintx = randmaxminInt(worldsizex - 5, 5);;
		int tempinty = randmaxminInt(worldsizey - 5, 5);
		world[tempintx + 1][tempinty] = true;
		world[tempintx + 2][tempinty] = true;
		world[tempintx + 3][tempinty] = true;
		world[tempintx][tempinty+1] = true;
		world[tempintx + 1][tempinty+1] = true;
		world[tempintx + 2][tempinty+1] = true;
		
	}
	
	private void spawnBeacon() {
		int tempintx = randmaxminInt(worldsizex - 10, 10);
		int tempinty = randmaxminInt(worldsizey - 10, 10);
		world[tempintx][tempinty] = true;
		world[tempintx + 1][tempinty] = true;
		world[tempintx][tempinty + 1] = true;
		world[tempintx + 1][tempinty + 1] = true;
		world[tempintx + 2][tempinty + 2] = true;
		world[tempintx + 3][tempinty + 2] = true;
		world[tempintx + 2][tempinty + 3] = true;
		world[tempintx + 3][tempinty + 3] = true;
	}
	
	private void spawncells() {
		//make whole world dedz
		for(boolean[] arr : world){
			Arrays.fill(arr, false);
		}
		for(boolean[] arr : futureworld){
			Arrays.fill(arr, false);
		}
		}
		
		
	private void update() {
		for (int i = 0; i < worldsizex; i++) {
			for (int j = 0; j < worldsizey; j++) {
				connectedCells = 0;
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
					chke(i,j);
					chkw(i,j);
				}
				//BOTTOM ROW
				else if (j == worldsizey - 1){
					chkn(i,j);
					chkne(i,j);
					chknw(i,j);
					chke(i,j);
					chkw(i,j);
				}
				//LEFT ROW
				else if (i == 0){
					chkne(i,j);
					chke(i,j);
					chkse(i,j);
					chkn(i,j);
					chks(i,j);
				}
				//RIGHT ROW
				else if (i == worldsizex -1){
					chkw(i,j);
					chknw(i,j);
					chksw(i,j);
					chkn(i,j);
					chks(i,j);
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
					if (connectedCells == 2 || connectedCells == 3){
						futureworld[i][j] = true;
					}
				}
				//IF CURRENT CELL IS DEAD
				if (!world[i][j]){
					if (connectedCells == 3){
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

	// DRAW TO BUFFER
	private void render() {
		// draw white background
		Color cl = Color.WHITE;
		crayon.setColor(cl);
		crayon.fillRect(0, 0, WIDTH, HEIGHT);
		//DRAW LIVE CELLS TO BOARD
		for (int i = 0; i < worldsizex; i++) {
			for (int j = 0; j < worldsizey; j++) {
				if (world[i][j] == true) {
					if (trippymode) {
						int tempNum = randmaxminInt(8, 1);
						switch (tempNum) {
						case 1:
							crayon.setColor(Color.CYAN);
							break;
						case 2:
							crayon.setColor(Color.GREEN);
							break;
						case 3:
							crayon.setColor(Color.BLUE);
							break;
						case 4:
							crayon.setColor(Color.PINK);
							break;
						case 5:
							crayon.setColor(Color.ORANGE);
							break;
						case 6:
							crayon.setColor(Color.MAGENTA);
							break;
						case 7:
							crayon.setColor(Color.YELLOW);
							break;
						case 8:
							crayon.setColor(Color.RED);
							break;
						
						}
						
					}
					else {crayon.setColor(Color.BLACK);}
					crayon.fillRect(i * CELLSIZE, j * CELLSIZE, CELLSIZE, CELLSIZE); // NEEDS WIDTH AND
				}
				else{
					crayon.setColor(Color.GRAY);
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
	
	private void flushfutureworld() {
		for(boolean[] arr : futureworld){
			Arrays.fill(arr, false);
		}
	}
	
	private void chknw(int inX, int inY){ 
		if (world[inX - 1][inY - 1])
			connectedCells++;
	}
	private void chkn(int inX, int inY){ 
		if (world[inX][inY - 1])
			connectedCells++;
	}
	private void chkne(int inX, int inY){ 
		if (world[inX + 1][inY - 1])
			connectedCells++;
	}
	private void chke(int inX, int inY){ 
		if (world[inX + 1][inY])
			connectedCells++;
	}
	private void chkw(int inX, int inY){ 
		if (world[inX - 1][inY])
			connectedCells++;
	}
	private void chksw(int inX, int inY){ 
		if (world[inX - 1][inY + 1])
			connectedCells++;
	}
	private void chks(int inX, int inY){ 
		if (world[inX][inY + 1])
			connectedCells++;
	}
	private void chkse(int inX, int inY){ 
		if (world[inX + 1][inY + 1])
			connectedCells++;
	}
	
	 //Called when the key is pressed down.
	public void keyPressed(KeyEvent key) {
		//AVOID EXCEPTION FAULT FROM SPAMMIUNG KEYDOWN
		if (!keyDown) {
			switch (key.getKeyCode()) {
			case KeyEvent.VK_0:
				for (int i = 0; i < worldsizex; i++) {
					for (int j = 0; j < worldsizey; j++) {
						if (randomBoolean()) {
							world[i][j] = true;
						}
					}
				}
				break;
			case KeyEvent.VK_1:
				spawnGlider();
				break;
			case KeyEvent.VK_2:
				spawnLWS();
				break;
			case KeyEvent.VK_3:
				spawnBlinker();
				break;
			case KeyEvent.VK_4:
				spawnToad();
				break;
			case KeyEvent.VK_5:
				spawnBeacon();
				break;
			case KeyEvent.VK_T:
				trippymode = true;
				break;
			case KeyEvent.VK_Y:
				trippymode = false;
				break;
			case KeyEvent.VK_SPACE:
				spawncells();
				break;
			case KeyEvent.VK_COMMA:
				FPS++;
				break;
			case KeyEvent.VK_H:
				drawHelp();
				break;
			case KeyEvent.VK_R:
				drawRules();
				break;
			}
		}
		keyDown = true;
		}
	
	private void drawWelcome() {
		String[] options = {"START"};
		JPanel panel = new JPanel();
		
		JLabel lbl = new JLabel("Welcome! Press H for controls");									
		panel.add(lbl);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Conway's Game Of Life", JOptionPane.NO_OPTION, JOptionPane.DEFAULT_OPTION, null, options , options[0]);
	}
	
	private void drawRules() {
		JOptionPane pane = new JOptionPane(
				"1) A cell is either live or dead\n"
						+ "2) Any live cell with fewer than two live neighbours dies, as if caused by under-population.\n"
						+ "3) Any live cell with two or three live neighbours lives on to the next generation.\n"
						+ "4) Any live cell with more than three live neighbours dies, as if by overcrowding.\n"
						+ "5) Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.");

		JDialog d = pane.createDialog("Conway's Game Of Life Rules");
		d.setLocation(320, 280);
		d.setVisible(true);
	}
	
	private void drawHelp() {
		String offsetToCenter = "               ";
		JOptionPane pane = new JOptionPane(offsetToCenter
				+ "0 - Randomize Cells\n" + offsetToCenter + "1 - Glider\n"
				+ offsetToCenter + "2 - Lightweight Ship\n" + offsetToCenter
				+ "3 - Blinker\n" + offsetToCenter + "4 - Toad            \n"
				+ offsetToCenter + "5 - Beacon Glider\n" + offsetToCenter
				+ "T - Trippy Mode On  \n" + offsetToCenter
				+ "Y - Trippy Mode Off\n" + offsetToCenter
				+ "R - Rules\n" + offsetToCenter
				+ "SPACEBAR - Restart  \n" + offsetToCenter

		);
		JDialog d = pane.createDialog("Spawning Guide");
		d.setLocation(320, 280);
		d.setVisible(true);
	
	}

	// Called when the key is released
	public void keyReleased(KeyEvent e) {
		keyDown = false;
	}

	// Called when a key is typed
	public void keyTyped(KeyEvent e) {
	}

}
