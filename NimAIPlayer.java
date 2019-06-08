import java.util.Random;
import java.util.Scanner;
/*
	NimAIPlayer.java
	
	This class is provided as a skeleton code for the tasks of 
	Sections 2.4, 2.5 and 2.6 in Project C. Add code (do NOT delete any) to it
	to finish the tasks. 
*/
/*
 * Dean Pakravan: 757389
 * Project C: COMP90041
 * May 2018
 */

public class NimAIPlayer extends NimPlayer implements Testable {
	// you may further extend a class or implement an interface
	// to accomplish the tasks.	

	private static final long serialVersionUID = -1910162330070416384L;
	private Random generator;
	
	public NimAIPlayer(String username, String givenname, String familyname) {
		super(username, givenname, familyname);
	}
	
	public int removeStone(NimPlayer instance, int currentStone, int upperbound, Scanner key) {
		System.out.printf("%n" + currentStone+ " stones left:");
		for (int i=1; i<=currentStone; i++) {				
			System.out.print(" *");	
		}
		System.out.printf("%n" + instance.getGiven() + "'s turn - remove how many?%n");
		
		//Find the greatest k value for k*(UP + 1) + 1 < currentStone
		int remove = (currentStone - 1)/(upperbound + 1);
		
		int minusStone = currentStone - (remove*(upperbound + 1) + 1);
		//If stone removal is out of bounds, generate a random number to remove
		if(minusStone>upperbound || minusStone<=0 || minusStone>currentStone) {
			//RANGE [1, upperbound]
			generator = new Random();
			//Generate random stone removal
			int randStone = generator.nextInt(upperbound) + 1;
		  	return currentStone -= randStone;
		} else {
		   	currentStone -= minusStone;
		   	return currentStone;
		}
	}
	
	public String advancedMove(boolean[] available, String lastMove) {
		// the implementation of the victory
		// guaranteed strategy designed by you
		String move = "";
		
		return move;
	}
} 
