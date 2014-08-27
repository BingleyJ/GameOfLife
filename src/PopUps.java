import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class PopUps {
	
	public PopUps(){
		
	}
	public		 void drawWelcome() {
		String[] options = {"START"};
		JPanel panel = new JPanel();
		
		JLabel lbl = new JLabel("Welcome! Press H for controls");									
		panel.add(lbl);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Conway's Game Of Life", JOptionPane.NO_OPTION, JOptionPane.DEFAULT_OPTION, null, options , options[0]);
	}
	
	public void drawRules() {
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
	
	public void drawHelp() {
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
}
