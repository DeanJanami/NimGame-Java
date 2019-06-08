/** Dean Pakravan: 757389 
 *  Project C: COMP90041
 *  Semester 1 May 2018  
 *
 */
import java.io.Serializable;
import java.util.Scanner;

public abstract class NimPlayer implements Serializable {
	//Generated Serial ID
	private static final long serialVersionUID = 919863449829537558L;
	//Information about each player
	private String username;
	private String givenname;
	private String familyname;
	private int wins;
	private int games;

	public NimPlayer(String username, String familyname, String givenname) {
		this.username = username;
		this.givenname = givenname;
		this.familyname = familyname;
		wins = 0;
		games = 0;
	}
	
	public void editName(String givenname, String familyname) {
		this.givenname = givenname;
		this.familyname = familyname;
	}
	
	public void resetStats() {
		wins = 0;
		games = 0;
	}
	
	public void addGameWin(boolean win) {
		if (win) {
			wins += 1;
			games += 1;
		}
		else {
			games += 1;
		}
	}

	public double getRanks() {
		double rank = 0.0;
		//If we haven't played any games; since we do no divide by zero
		if (games == 0) {
			return rank;
		}
		else {
			//Multiply by 100 to get percentage
			rank = (wins*100.0)/games ;
			return rank;
		}			
	}
	
	//Used in displayplayer
	public void printPlayers() {
		System.out.printf(username + "," + givenname
    			+ "," + familyname+"," + games + " games," 
    			+ wins + " wins%n");	
	}
	
	//Used in rankings
	public void printRanks() {
		//Round percentage to nearest integer
		long value = Math.round(getRanks());
		//Convert to string, easier to print 5 spaces for percentage
		String rank = Long.toString(value) + "%";
		System.out.printf("%-5s" + "| %02d"   + " games | " +
				givenname+ " " + familyname + "%n", rank, games);
	}

	public abstract int removeStone(NimPlayer instance, int currentStone, 
									int upperbound, Scanner key);
	
	//Replay the hand if the player goes out of bounds for stone removal.
	public void replay(int currentStone, int upperBound) {
		System.out.printf("%nInvalid move. "
				+ "You must remove between 1 and ");
		//print whichever value is currently smaller, upperbound or current stone count
		int bound = Math.min(currentStone, upperBound);
		System.out.printf(bound + " stones.%n");
	}
	
	//Our getters
	public String getGiven() {
		return new String(givenname);
	}
	
	public String getFamily() {
		return new String(familyname);
	}
	
	public String getUser() {
		return new String(username);
	}
	
	public int getGame() {
		return games;
	}
	
	public int getWin() {
		return wins;
	}

}