package goose.engine;

public class ConsoleGameWopr extends ConsoleGame {
	
	
	public ConsoleGameWopr(long seed) {
		super(seed);
	}

	public void run() {
		try {
			System.out.println("Goose Game - Wopr mode");
			
			int numPlayers = rnd.nextInt(9)+1;
			while (numPlayers > 0) {
				g.addPlayer(Character.toString('a' + numPlayers - 1));
				numPlayers--;
			}
			
			moveWopr();
			
			printStatus();
			
			System.out.println("end ?");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
