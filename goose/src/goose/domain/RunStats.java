package goose.domain;

public class RunStats {
	
	int numPlayers;
	int winningPlayer;
	int dicesRolled;
	
	public RunStats(int numPlayers, int winningPlayer, int dicesRolled) {
		super();
		this.numPlayers = numPlayers;
		this.winningPlayer = winningPlayer;
		this.dicesRolled = dicesRolled;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public int getWinningPlayer() {
		return winningPlayer;
	}

	public int getDicesRolled() {
		return dicesRolled;
	}
	
	public String toString() {
		return numPlayers + "\t" + winningPlayer + "\t" + dicesRolled;
	}
}
