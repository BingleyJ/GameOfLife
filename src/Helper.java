import java.util.Random;


public class Helper {

	
	public Helper(){
		
	}
	

	public boolean randomBoolean(){
	    return Math.random() < 0.5;
	}
	
	public int randmaxminInt(int max, int min) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
		
}
