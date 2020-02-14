package goose.engine;

import goose.domain.Move;
import goose.domain.RunStats;

public class ConsoleGameWoprStats extends ConsoleGame {
	
	
	public ConsoleGameWoprStats(long seed) {
		super(seed);
	}

	RunStats stats;
	
	public void run() {
		try {
			int numPlayers = rnd.nextInt(9)+1;
			while (numPlayers > 0) {
				g.addPlayer(Integer.toString(numPlayers));
				numPlayers--;
			}
			
			Move wm = moveWoprSilently();
			
			stats = new RunStats(g.getPlayers().size(), Integer.parseInt(wm.getPlayer().getName()), wm.getPlayer().getDiceRolled());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RunStats getStats() {
		return stats;
	}
	
}
