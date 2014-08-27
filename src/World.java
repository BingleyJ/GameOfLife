import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;


public class World {
	private int worldSizeX;
	private int worldSizeY;
	private boolean [][] world;
	private boolean [][] futureWorld;;
	public int cellSize = 10;
	private int connectedCells = 0;
	private Helper helper = new Helper();
	private boolean trippyMode = false;

	
	public World(int inWidth, int inHeight){
		this.worldSizeX = inWidth / 10;
		this.worldSizeY = inHeight / 10;
		world = new boolean [worldSizeX][worldSizeY];
		futureWorld = new boolean [worldSizeX][worldSizeY];
		spawncells();
		
	}
	
	public void update(){
		for (int i = 0; i < worldSizeX; i++) {
			for (int j = 0; j < worldSizeY; j++) {
				connectedCells = 0;
				//TOP LEFY CORNER OF ARRAY
				if (i == 0 && j == 0){
					chks(i,j);
					chke(i,j);
					chkse(i,j);
				}
				//TOP RIGHT CORNER OF ARRAY
				else if (i == worldSizeX - 1 && j == 0){
					chkw(i,j);
					chksw(i,j);
					chks(i,j);
				}
				//BOTTOM LEFT
				else if (i == 0 && j == worldSizeY -1){
					chkn(i,j);
					chkne(i,j);
					chke(i,j);
				}
				//BOTTOM RIGHT
				else if (i== worldSizeX - 1 && j == worldSizeY - 1){
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
				else if (j == worldSizeY - 1){
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
				else if (i == worldSizeX -1){
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
						futureWorld[i][j] = true;
					}
				}
				//IF CURRENT CELL IS DEAD
				if (!world[i][j]){
					if (connectedCells == 3){
						futureWorld[i][j] = true;
					}
				}
			}
		}
		//COPY FUTURE TO PRESENT WORLD
		for (int i = 0; i < worldSizeX - 1; i++){
			for (int j = 0; j < worldSizeY - 1; j++){
				world[i][j] = futureWorld[i][j];
			}
		}
		flushfutureworld();
		
	}
	
	public void Draw(Graphics2D crayon){
		for (int i = 0; i < worldSizeX; i++) {
			for (int j = 0; j < worldSizeY; j++) {
				if (world[i][j] == true) {
					if (trippyMode) {
						int tempNum = helper.randmaxminInt(8, 1);
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
					crayon.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
				}
				else{
					crayon.setColor(Color.GRAY);
					crayon.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
				}
			}
		}
	}
	
	public void spawnGlider(){
		int tempintx = helper.randmaxminInt(worldSizeX - 10, 10);
		int tempinty = helper.randmaxminInt(worldSizeY - 10, 10);
			//SE BOUND
			world[tempintx][tempinty] = true;
			world[tempintx + 2][tempinty] = true;
			world[tempintx + 2][tempinty + 1] = true;
			world[tempintx + 1][tempinty + 1] = true;
			world[tempintx + 1][tempinty + 2] = true;
	}
	
	public void spawnLWS(){
		int tempintx = helper.randmaxminInt(worldSizeX - 10, 10);
		int tempinty = helper.randmaxminInt(worldSizeY - 10, 10);
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
	
	public void spawnBlinker(){
		int tempintx = helper.randmaxminInt(worldSizeX - 10, 10);
		int tempinty = helper.randmaxminInt(worldSizeY - 10, 10);
		world[tempintx][tempinty] = true;
		world[tempintx][tempinty + 1] = true;
		world[tempintx][tempinty + 2] = true;
	}
	
	public void spawnToad(){
		int tempintx = helper.randmaxminInt(worldSizeX - 5, 5);;
		int tempinty = helper.randmaxminInt(worldSizeY - 5, 5);
		world[tempintx + 1][tempinty] = true;
		world[tempintx + 2][tempinty] = true;
		world[tempintx + 3][tempinty] = true;
		world[tempintx][tempinty+1] = true;
		world[tempintx + 1][tempinty+1] = true;
		world[tempintx + 2][tempinty+1] = true;
		
	}
	
	public void spawnBeacon() {
		int tempintx = helper.randmaxminInt(worldSizeX - 10, 10);
		int tempinty = helper.randmaxminInt(worldSizeY - 10, 10);
		world[tempintx][tempinty] = true;
		world[tempintx + 1][tempinty] = true;
		world[tempintx][tempinty + 1] = true;
		world[tempintx + 1][tempinty + 1] = true;
		world[tempintx + 2][tempinty + 2] = true;
		world[tempintx + 3][tempinty + 2] = true;
		world[tempintx + 2][tempinty + 3] = true;
		world[tempintx + 3][tempinty + 3] = true;
	}
	
	public void spawncells() {
		//make whole world dedz
		for(boolean[] arr : world){
			Arrays.fill(arr, false);
		}
		for(boolean[] arr : futureWorld){
			Arrays.fill(arr, false);
		}
		
	}

	public int getWorldsizex() {
		return worldSizeX;
	}

	public void setWorldsizex(int worldsizex) {
		this.worldSizeX = worldsizex;
	}

	public int getWorldsizey() {
		return worldSizeY;
	}

	public void setWorldsizey(int worldsizey) {
		this.worldSizeY = worldsizey;
	}
	

	private void flushfutureworld() {
		for(boolean[] arr : futureWorld){
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
	public void randomWorld(){
		for (int i = 0; i < worldSizeX; i++) {
			for (int j = 0; j < worldSizeY; j++) {
				if (helper.randomBoolean()) {
					world[i][j] = true;
				}
			}
		}
	}
	
	public void setTrippymode(boolean trippymode) {
		this.trippyMode = trippymode;
	}
}
