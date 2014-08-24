import javax.swing.JFrame;

/*
Title: GameOfLife
Professor : Lonnie Bowe
Name : John Bingley 
Class : DataStructures
Purpose : Simulate Conway's Degree Of Awesome
Check READ.md for more info
 */

public class LifeSim {
	
	public static void main(String[] args){
		
		JFrame window = new JFrame("Conway's Game Of Life");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setContentPane(new LifePanel());
		
		window.pack();
		window.setVisible(true);
		window.setResizable(true);
		
	}

}
