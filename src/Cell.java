import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Cell {
	
	private int x;
	private int y;
	private int width = 10;
	private int height = 10;
	private Rectangle cellRect;

	private boolean dead = false;
	private Color color = Color.BLUE;
	
	public Cell(int x, int y){
		this.x = x;
		this.y = y;
		cellRect = new Rectangle(x, y, width, height);
		
	}
	
	public int getx() {return x;}
	public int gety() {return y;}
	public boolean isDead() {return dead;}
	public void kill(){dead = true;}
	public void unkill(){dead = false;}
	
	public int getWorldPosition() {
		int position = 0;
		if (y == 0){
			position = (x / 10);
		}
		if (y > 0){
			int tempx = x / 10;
			
			position = (40 * (y / 10)) + tempx ;
		}
		return position;		
	}
	
	public void update() {
	
	}
	
	public void draw(Graphics2D g) {
		if (isDead()){
			g.setColor(Color.GRAY);
			g.draw(cellRect);
			g.fillRect(x, y, width, height);
		}
		if (!isDead()) {
			g.setColor(color);
			g.draw(cellRect);

		//	g.setStroke(new BasicStroke(2));
		//	g.setColor(color.darker());
		//	g.draw(cellRect);

	//		g.setStroke(new BasicStroke(1));
		}
	}
	
	
}
