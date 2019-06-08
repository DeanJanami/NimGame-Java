import java.util.Scanner;
/*
 * Dean Pakravan: 757489
 * Project C
 * May 2018
 * Semester 1
 * 
 * Human Players of Nim have their removeStone method here.
 * Rest of methods are inherited from NimPlayer
 */
public class NimHumanPlayer extends NimPlayer{

	private static final long serialVersionUID = -2002034158689501827L;
	
	//Constructor
	public NimHumanPlayer(String username, String givenname, String familyname) {
		super(username, givenname, familyname);
	}

	public int removeStone(NimPlayer instance, int currentStone, int upperbound, Scanner key) {
		while (true) {
			//Print the remaining stones
			System.out.printf("%n" + currentStone+ " stones left:");
			for (int i=1; i<=currentStone; i++) {				
				System.out.print(" *");	
			}
			System.out.printf("%n" + instance.getGiven() + "'s turn - remove how many?%n");
			try {
			int minusStone = Integer.parseInt(key.nextLine());
			//If the Player's removal stone goes out of bounds, replay his/her turn
			//Needs exception
			if (minusStone>upperbound || minusStone<=0 || minusStone>currentStone) {
				throw new Exception();
			}
			else {
				currentStone -= minusStone;
				return currentStone;
			}
			}
			//If stone removal is not an positive integer
			catch (NumberFormatException e) {
				replay(currentStone, upperbound);
				continue;
			//If stone removal goes beyond the upper bound.
			} catch (Exception e) {
				replay(currentStone, upperbound);
				continue;
			} 
			
		}
	}

}
