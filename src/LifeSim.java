import javax.swing.JFrame;


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
