import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;


public class World {
	private int worldSizeX;
	private int worldSizeY;
	private boolean [][] world;
	private boolean [][] futureWorld;
	boolean inBounds;
	public int cellSize = 10;
	private int connectedCells = 0;
	private Helper helper = new Helper();
	private boolean trippyMode = false;

	
	public World(int inWidth, int inHeight){
		this.worldSizeX = //inWidth / 10;
		this.worldSizeY = inHeight / 10;
		world = new boolean [worldSizeX][worldSizeY];
		futureWorld = new boolean [worldSizeX][worldSizeY];
		spawncells();
		
	}
	
	public void testCellCount(int inX, int inY){
		int count = 0;
		int i = inX;
		int j = inY;
		if (chknw(i,j))
			count++;
		if (chkn(i,j))
			count++;
		if (chkne(i,j))
			count++;
		if (chkw(i,j))
			count++;
		if (chke(i,j))
			count++;
		if (chksw(i,j))
			count++;
		if (chks(i,j))
			count++;
		if (chkse(i,j))
			count++;
		System.out.println("Connected cells to X" + i + " Y" + j + " = " + count);
		if (world[i][j])
			System.out.println("Cell is Alive");
		if (!world[i][j])
			System.out.println("Cell is Dead");

	}
	
	
	public void update(){
		for (int i = 0; i < worldSizeX; i++) {
			for (int j = 0; j < worldSizeY; j++) {
				connectedCells = 0;
					if(chknw(i,j))
						connectedCells++;
					if(chkn(i,j))
						connectedCells++;
					if(chkne(i,j))
						connectedCells++;
					if(chkw(i,j))
						connectedCells++;
					if(chke(i,j))
						connectedCells++;
					if(chksw(i,j))
						connectedCells++;
					if(chks(i,j))
						connectedCells++;
					if(chkse(i,j))	
						connectedCells++;

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
		for (int i = 0; i < worldSizeX ; i++){
			for (int j = 0; j < worldSizeY; j++){
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
	
	
	public void spawnBottomThreeTest(){
		world[30][worldSizeY - 1] = true;
		world[32][worldSizeY - 1] = true;
		world[31][worldSizeY - 2] = true;

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

	private boolean chknw(int inX, int inY){ 
		boolean hasFriend = false;
		if ((inX - 1 >= 0 && inX < world.length) && (inY - 1 >= 0 && inY < world[inX].length)){
			if (world[inX - 1][inY - 1])
				hasFriend = true;
		}
		return hasFriend;
	}

	private boolean chkn(int inX, int inY){ 
		boolean hasFriend = false;
		if ((inX >= 0 && inX < world.length) && (inY - 1 >= 0 && inY < world[inX].length)) {
			if (world[inX][inY - 1])
				hasFriend = true;		}
		return hasFriend;}
	private boolean chkne(int inX, int inY){ 
		boolean hasFriend = false;
		if ((inX >= 0 && inX + 1 < world.length) && (inY - 1 >= 0 && inY < world[inX].length)) {
			if (world[inX + 1][inY - 1])
				hasFriend = true;		}
		return hasFriend;}
	private boolean chke(int inX, int inY){
		boolean hasFriend = false;
		if ((inX >= 0 && inX + 1 < world.length) && (inY >= 0 && inY < world[inX].length)) {
			if (world[inX + 1][inY])
				hasFriend = true;		}
		return hasFriend;}
	private boolean chkw(int inX, int inY){ 
		boolean hasFriend = false;
		if ((inX - 1 >= 0 && inX < world.length) && (inY >= 0 && inY < world[inX].length)) {
			if (world[inX - 1][inY])
				hasFriend = true;		}
		return hasFriend;}
	private boolean chksw(int inX, int inY){ 
		boolean hasFriend = false;
		if ((inX - 1 >= 0 && inX < world.length) && (inY >= 0 && inY + 1 < world[inX].length)) {
			if (world[inX - 1][inY + 1])
				hasFriend = true;		}
		return hasFriend;}
	private boolean chks(int inX, int inY) {
		boolean hasFriend = false;
		if ((inX >= 0 && inX < world.length) && (inY >= 0 && inY + 1 < world[inX].length)) {
			if (world[inX][inY + 1])
				hasFriend = true;		}
		return hasFriend;}
	private boolean chkse(int inX, int inY){ 
		boolean hasFriend = false;
		if ((inX >= 0 && inX + 1 < world.length) && (inY >= 0 && inY + 1 < world[inX].length)) {
			if (world[inX + 1][inY + 1])
				hasFriend = true;		}
		return hasFriend;}
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
