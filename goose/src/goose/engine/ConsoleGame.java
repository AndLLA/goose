package goose.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import goose.domain.Move;
import goose.domain.Player;

public class ConsoleGame {
	
	Random rnd = new Random();
	Game g = new Game();
	
	public ConsoleGame() {
		rnd.setSeed(123);
	}
	
	public ConsoleGame(long seed) {
		rnd.setSeed(seed);
	}	

	final static String cmdAddPlayer = "add player";
	final static String cmdMove = "move";
	
	public void run() throws IOException, GameException {
		System.out.println("Goose Game");
		
		BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
		Pattern ptrnMove = Pattern.compile("^(move)(\\ )([a-zA-Z0-9]+)(\\ )([1-6]{1})([\\,\\ ]+)([1-6]{1})$");
		Pattern ptrnMoveNoDice = Pattern.compile("^(move)(\\ )([a-zA-Z0-9]+)$");
		
		while (!g.isFinished()) {
			String cmd = rdr.readLine();
			if (cmd == null)
				break;
			
			if (cmd.startsWith("help")) {
				System.out.println("available commands: help, exit, status, add player, move, wopr");
				continue;
			}
			
			if (cmd.startsWith("exit")) {
				break;
			}
			
			if (cmd.startsWith("status")) {
				printStatus();
				continue;
			}			
			
			if (cmd.startsWith(cmdAddPlayer)) {
				String pn = cmd.substring(cmdAddPlayer.length()+1).trim(); 
				try {
					if (!Pattern.matches("[a-z,A-Z,0-9]+", pn))
						throw new GameException(pn + ": invalida name");
					
					g.addPlayer(pn);
					System.out.println("Players: " + g.getPlayersNames());
				} catch (GameException e) {
					System.out.println(e.getMessage());
				}
				continue;
			}
			
			if (cmd.startsWith(cmdMove)) {
				try {
					cmd = cmd.trim();
					
					Matcher cmdMatcher = ptrnMove.matcher(cmd);
					if (cmdMatcher.matches()) {
						String playerName = cmdMatcher.group(3);
						int rollA = Integer.parseInt(cmdMatcher.group(5));
						int rollB = Integer.parseInt(cmdMatcher.group(7));
						movePlayer(playerName, rollA, rollB);
						continue;
					}
					
					Matcher cmdMatcherNodice = ptrnMoveNoDice.matcher(cmd);
					if (cmdMatcherNodice.matches()) {
						String playerName = cmdMatcherNodice.group(3);
						int rollA = rnd.nextInt(6)+1;
						int rollB = rnd.nextInt(6)+1;
						movePlayer(playerName, rollA, rollB);
						continue;
					}
					
					System.out.println(cmd + ": Malformed move command");
				} catch (NumberFormatException e) {
					System.out.println(cmd + ": Malformed move command " + e.getMessage());
				}
				continue;				
			}
			
			if (cmd.startsWith("wopr")) {
				try {
					moveWopr();
				} catch (GameException e) {
					System.out.println(e.getMessage());
				}
				continue;
			}
			
			System.out.println(cmd + ": unrecognized command");
		}
		
		printStatus();
		
		System.out.println("end ?");
	}

	private void movePlayer(String playerName, int rollA, int rollB) {
		try {
			List<Move> lMoves = g.move(playerName, rollA, rollB);
			
			StringBuilder bld = new StringBuilder();
			bld.append(lMoves.get(0).getPlayer().getName()).append(" rolls ").append(rollA).append(", ").append(rollB);
			
			for (Move m : lMoves) {
				String sFrom = Integer.toString(m.getFrom());
				switch(m.getFrom()) {
				case 0:
					sFrom = "Start";
					break;
				}
				
				String sTo = Integer.toString(m.getTo());
				switch(m.getTo()) {
				case 0:
					sTo = "Start";
					break;
				case 6:
					sTo = "The Bridge";
					break;
				}
				
				switch(m.getType()) {
				case dice:
					bld.append(". ").append(m.getPlayer().getName()).append(" moves from ").append(sFrom).append(" to ").append(sTo);
					break;
					
				case winner:
					bld.append(". ").append(m.getPlayer().getName()).append(" Wins!!");
					break;
					
				case bridge:
					bld.append(". ").append(m.getPlayer().getName()).append(" jumps to ").append(sTo);
					break;
					
				case goose:
					bld.append(", The Goose. ").append(m.getPlayer().getName()).append(" moves again and goes to ").append(sTo);
					break;
					
				case prank:
					bld.append(". On ").append(sFrom).append(" there is ").append(m.getPlayer().getName()).append(", who returns to ").append(sTo);
					break;
					
				case bounce:
					bld.append(". ").append(m.getPlayer().getName()).append(" bounces! ").append(m.getPlayer().getName()).append(" returns to ").append(sTo);							
					break;
				}
			}
			System.out.println(bld.toString());
			
		} catch (GameException e) {
			System.out.println(e.getMessage());
		}
	}
	
	protected void moveWopr() throws GameException {
		if (g.getPlayers().isEmpty())
			throw new GameException("no players");
			
		while (!g.isFinished()) {
			for (Player p : g.getPlayers()) {
				if (g.isFinished())
					break;
				
				int rollA = rnd.nextInt(6)+1;
				int rollB = rnd.nextInt(6)+1;
				
				movePlayer(p.getName(), rollA, rollB);
			}
			
		}
	}
	
	protected Move moveWoprSilently() throws GameException {
		List<Move> bufMoves = null;
		while (!g.isFinished()) {
			for (Player p : g.getPlayers()) {
				if (g.isFinished())
					break;
				
				int rollA = rnd.nextInt(6)+1;
				int rollB = rnd.nextInt(6)+1;

				bufMoves = g.move(p.getName(), rollA, rollB);
			}
		}
		return bufMoves.get(bufMoves.size()-1);
	}
	
	
	protected void printStatus() {
		for (Player p : g.getPlayers()) {
			System.out.println(p.getName() + "(" + p.getDiceRolled() + "," + p.getNumMoves() + "," + p.getPosition() + (p.winner() ? ",winner" : "") + ")");
		}
	}
	
}
