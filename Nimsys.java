/*  Dean Pakravan: 757389 
 *  Project C: COMP90041
 *  Semester 1 May 2018  
 *
 *  Nimsys provides the basis of playing Nim and maintaining a collection of players.
 */

import java.util.InputMismatchException;
import java.util.Scanner;
//Exceptions for statistic file storing
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Nimsys {
	private Scanner key;
	//When we scan for a username, we record its index location.
	private int playerIndex;
	//Variable used in findNull method
	private int nullLimit;
	//Array to hold our instances
	private NimPlayer[] players;
	
	//Finds the the null instance NimPlayer with the lowest index
	//Hence if we have less than the maximum number of players, we can be more efficient
	private void findNull(NimPlayer[] inputArray) {
		nullLimit = 0;
		for (int i=0; i<inputArray.length; i++) {
			if (inputArray[i]==null) {
				nullLimit = i;
				break;
			}
		}
	}
	
	public Nimsys() {
		key = new Scanner(System.in);
		try {
		ObjectInputStream inputStream =
				new ObjectInputStream(new FileInputStream("players.dat"));
		players = (NimPlayer[])inputStream.readObject( );
		inputStream.close();
		}
		  catch(FileNotFoundException e) {
			players = new NimPlayer[100];
		} catch(InstantiationError e) {
			players = new NimPlayer[100];
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	//Completes selection sort for displayplayer
    private void doSelectionSort(NimPlayer[] players){
        for (int i = 0; i < nullLimit- 1; i++) {
            int index = i;
            for (int j = i + 1; j < nullLimit; j++) {
                if (players[j].getUser().compareTo(players[index].getUser())<0) {
                    index = j;
                }
            }   
            NimPlayer temp = players[index];  
    		players[index] = players[i];
    		players[i] = temp;  
        }
    }
    
    //This is selection sort for the rankings
    private void doRankSort(NimPlayer[] players, String statement) {
        for (int i = 0; i < nullLimit - 1; i++)
        {
           int index = i;
           for (int j = i+1; j < nullLimit; j++) {
        	    //If user asks "rankings asc"
	            if("increasing".equals(statement)) {
	            	if (players[j].getRanks() < players[index].getRanks()) { 
		               index = j;
		            //If users have the same rank, we sort alphabetically
		            } else if (players[j].getRanks() == players[index].getRanks()
		               		   && players[j].getUser().compareTo(players[index].getUser())<0){
		                	index = j;
		            }   
	            }
	            //If we sort decreasing instead
		        else {
		        	if (players[j].getRanks() > players[index].getRanks()) {
		        		index = j;
			          //If users have the same rank, we sort alphabetically
		        	} else if (players[j].getRanks() == players[index].getRanks()
	               		   	   && players[j].getUser().compareTo(players[index].getUser())<0){
	                	index = j;
	                }                                
	           }
           }
           //Temp variable used in Selection Sort.
           NimPlayer temp = players[index];  
           players[index] = players[i];
           players[i] = temp;
        }
    }  
    
    //Check if specific username is an instance in the Players array
    private boolean checkUsername(NimPlayer[] players, String userTest) {
    	findNull(players);
    	for (int i=0; i<nullLimit; i++) {
    		if (players[i].getUser().equals(userTest)) {
    			//Record the index if the user exists
    			playerIndex = i;
    			return true;
    		}
    	}
    	return false;
    }
    
    //Create an instance in Players array if Username doesn't exist
    private void addPlayer(String[] userInfo) {
    	if(checkUsername(players,userInfo[1])) {
    		System.out.printf("The player already exists.%n");
    	} else {
    		//Find the a null instance with the lowest index
    		findNull(players);
    		//Create a new instance in NimPlayer and add user, given and family name
    		//Check if we add a human or AI player
    		if ("addplayer".equals(userInfo[0])) {
    			players[nullLimit] = new NimHumanPlayer(userInfo[1],userInfo[2],userInfo[3]);
    		} else {
    			players[nullLimit] = new NimAIPlayer(userInfo[1],userInfo[2],userInfo[3]);
    		}
    	}
    }
    
    //Remove a specific player or all of them
    private void removePlayer(String[] statement) {
    	//If we want to remove all players
    	if (statement.length==1) {
			System.out.printf("Are you sure you want to remove all players? (y/n)%n");
			String answer = key.nextLine();
			if ("y".equals(answer)) {
				players = new NimPlayer[100];
			}
		//If we want to remove a specific player
		} else if(checkUsername(players,statement[1])){
			players[playerIndex]=null; 

			//So we don't leave null instances between exisitng players
			for (int i=playerIndex; i<players.length-1; i++) {
				players[i] = players[i+1];
			}
			//Since we never reach the last entry
			players[players.length-1]=null;
		} else {
				System.out.printf("The player does not exist.%n");
			}	
    }
    
    //edit a player if it exists
    private void editPlayer(String username, String family, String first) {
        if(checkUsername(players, username)){
        	players[playerIndex].editName(first, family);
        } else {
        	System.out.printf("The player does not exist.%n");
        }   
    }
    
    //Reset games and wins in NimPlayer instances
    private void resetStats(String[] statement) {
    	if (statement.length==1) {
    		System.out.printf("Are you sure you want to reset all player statistics? (y/n)%n");
			String answer = key.nextLine();
			//If we wish to repeat the game
			if ("y".equals(answer)) {
				findNull(players);
				for (int i=0; i<nullLimit; i++) {
					players[i].resetStats();
				}
			}
		//If we reset a specific players stats
		} else {
			String[] userInfo = statement[1].split(",");    
	        if(checkUsername(players,userInfo[0])){
	        	players[playerIndex].resetStats();

	        } else {
	        	System.out.printf("The player does not exist.%n");
	        }
		}
    }
    
    //Display all players or a specific player
    private void displayPlayer(String[] statement) {
    	if (statement.length==1) { 
    		//So we don't display null entries
    		findNull(players);
    		doSelectionSort(players);
			
			//Display all players
			for (int i =0; i<nullLimit;i++) {
				players[i].printPlayers();
			}
			
		//Display a specific player if they exist
		} else {
			String[] username = statement[1].split(",");	
	        if(checkUsername(players,username[0])){
	        	players[playerIndex].printPlayers();
	        } else {
	        	System.out.printf("The player does not exist.%n");
	          }		
		}
    }
    
    //Show rankings of up to 10 players
    private void rankings(String[] statement) {
    	findNull(players);
		if (statement.length==1 || "desc".equals(statement[1])) {
			doRankSort(players,"decreasing");			
		} else {
			doRankSort(players,"increasing");
		}
		for (int i=0;i<nullLimit && i<10;i++) {
			players[i].printRanks();
    	}
    }
  
    //Setting up and playing Nim
    private void setupGame(String[] userInfo) {
    	//Return true if player1 username exists. Same for player2
    	Boolean P1Exist = checkUsername(players, userInfo[3]);
    	int p1index = playerIndex;
    	Boolean P2Exist = checkUsername(players, userInfo[4]);
    	int p2index = playerIndex;
    	if (P1Exist && P2Exist) {
    		//Create a new NimGame instance for each new game we play
    		//Providing the current and upper bound stone plus player index to provide names
        	NimGame game = new NimGame(userInfo, players[p1index], players[p2index]);
    		game.gameWelcome();
    		//Return the winning player after each game
    		Boolean Player1wins = game.gameplay(key);
    		if (Player1wins) {
    			//Add +1 to games for both players and +1 to wins for the winning player
    			players[p1index].addGameWin(true);
    			players[p2index].addGameWin(false);
    			System.out.println(players[p1index].getGiven()+ " " + 
    			players[p1index].getFamily() + " wins!");
    		}
    		else {
    			players[p2index].addGameWin(true);
    			players[p1index].addGameWin(false);
    			System.out.println(players[p2index].getGiven()+ " " + 
    			players[p2index].getFamily() + " wins!");
    		}
    	}
    	else {
    		System.out.printf("One of the players does not exist.%n");
    	}
    }
    
    private void setupNim(Nimsys Nimsys) {
    	//While we play Nim. "exit" to close the program
    	while (true) {
    	System.out.printf("%n$");
    	String statement = key.nextLine();
    	String[] mode = statement.split(" |,");
    	//List of commands
    	try {
    		if ("addplayer".equals(mode[0]) || "addaiplayer".equals(mode[0])) {
    			Nimsys.addPlayer(mode);
    			continue;
    					
    		} else if ("editplayer".equals(mode[0])) {
    				Nimsys.editPlayer(mode[1],mode[2],mode[3]);
    				 continue;
    				 
    		} else if ("startgame".equals(mode[0])) {
    				Nimsys.setupGame(mode);
    				continue;

    		} else if ("rankings".equals(mode[0])) {
    	    		Nimsys.rankings(mode);
    	    		continue;
    	    } else if ("resetstats".equals(mode[0])) {
	    			Nimsys.resetStats(mode);
	    			continue;
	    			
    	    } else if ("displayplayer".equals(mode[0])) {
	    			Nimsys.displayPlayer(mode);
	    			continue;
	    	
    		} else if ("removeplayer".equals(mode[0])) {
					Nimsys.removePlayer(mode);
					continue;
	    		
    		} else if ("exit".equals(mode[0])) {
    				try {
    				ObjectOutputStream outputStream = 
    						new ObjectOutputStream(new FileOutputStream("players.dat"));
    				//Stores the data file.
    				outputStream.writeObject(players);
    				outputStream.close();
    				}
    				catch(IOException e) {
    					System.out.printf("Players Array not found%n");
    				}
    				System.out.println();
    				System.exit(0);
    		}
    		else {
    			throw new InputMismatchException(mode[0]);
    		}
    	}
    	catch(ArrayIndexOutOfBoundsException e) {
			System.out.printf("Incorrect number of arguments supplied to command.%n");
		}
    	//If we don't enter any of the known commands
    	catch(InputMismatchException e) { 
    		System.out.printf("'"+e.getMessage() + "' is not a valid command.%n");
    	}
    	}
    }
    
	public static void main(String[] args) {
	System.out.printf("Welcome to Nim%n");
	Nimsys Nimsys = new Nimsys();
	Nimsys.setupNim(Nimsys);
}
}




 

