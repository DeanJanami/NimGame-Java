import java.util.Scanner;

/** Dean Pakravan: 757389
 *  Project C: COMP90041
 *  Semester 1 May 2018
 *
 * NimGame holds a single instance by the command 'startgame' in Nimsys.
 * Gameplay function for Nim is held in this class.
 */
public class NimGame {
	private int upperBound, currentStone;
	private Boolean P1;
	private NimPlayer player1,player2;
	
	//get the player names, initial stone and upper bound from Nimsys
	public NimGame(String[] gameInfo, NimPlayer player1, NimPlayer player2) {
		this.player1 = player1;
		this.player2 = player2;
		//Convert from string to integer
		currentStone = Integer.parseInt(gameInfo[1]);
		upperBound = Integer.parseInt(gameInfo[2]);
	}
	
	public void gameWelcome() {
		//Message at the beginning of each Nim Game
		System.out.printf("%nInitial stone count: " + currentStone + "%n" +
			"Maximum stone removal: " + upperBound + "%n" +
			"Player 1: " + player1.getGiven() + " " + player1.getFamily() +"%n" +
			"Player 2: " + player2.getGiven() + " " + player2.getFamily() +"%n");
	} 
	
	public boolean gameplay(Scanner key) {
		//While there are stones on the board
		P1 = true;
		while (currentStone>0) {
			//Player 1's turn		
			if (P1==true) {
				currentStone = player1.removeStone(player1, currentStone, upperBound, key);
				P1 = false;
			}				
			//Player 2's turn
			else {
				currentStone = player2.removeStone(player2, currentStone, upperBound, key);
				P1 = true;
				}
			}	
		System.out.printf("%nGame Over%n");
		return P1;
		}
}
	



