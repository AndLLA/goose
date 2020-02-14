package goose.engine;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import goose.domain.GameStatus;
import goose.domain.Move;
import goose.domain.MoveType;
import goose.domain.Player;

public class Game {
	HashMap<String, Player> mapPlayers = new HashMap<>();
	
	GameStatus status = GameStatus.config; 
	
	static final int WinningPosition = 63;
	
	public Game() {
	}

	/*
		https://github.com/xpeppers/goose-game-kata
	 */
	
	//
	// 1. Configuration - Add Players
	//
	
	public void addPlayer(String name) throws GameException {
		if (!status.equals(GameStatus.config))
			throw new GameException(status + ": invalid status");
		
		if (mapPlayers.containsKey(name))
			throw new GameException(name + ": already existing player");
		
		mapPlayers.put(name, new Player(name));
	}
	
	//
	// 2. moves
	//
	public List<Move> move(String playerName, int rollA, int rollB) throws GameException {
		List<Move> res = move(playerName, rollA, rollB, MoveType.dice);
		res.get(0).getPlayer().incdiceRolled();
		return res;
	}
	
	private List<Move> move(String playerName, int rollA, int rollB, MoveType moveType) throws GameException {
		if (status.equals(GameStatus.finished))
			throw new GameException(status + ": invalid status");
		if (mapPlayers.size() < 1)
			throw new GameException("no players configured");
		
		status = GameStatus.running;
		
		Player p = mapPlayers.get(playerName);
		if (p == null)
			throw new GameException(playerName + ": unrecognized player");
		
		if (rollA < 1 || rollA > 6)
			throw new GameException(rollA + ": invalid dice roll A");
		if (rollB < 1 || rollB > 6)
			throw new GameException(rollB + ": invalid dice roll B");
		
		LinkedList<Move> res = new LinkedList<Move>();
		
		final int prevPos = p.getPosition();
		int newPos = prevPos + rollA + rollB;
		
		//
		// handle winning and bounce back
		//
		if (newPos == WinningPosition) {
			res.add(p.addMove(prevPos, newPos, moveType));
			res.add(p.addMove(newPos, newPos, MoveType.winner));
			status = GameStatus.finished;
			return res;
		} else if (newPos > WinningPosition) {
			res.add(p.addMove(prevPos, WinningPosition, moveType));			
			newPos = WinningPosition + WinningPosition - newPos;
			res.add(p.addMove(WinningPosition, newPos, MoveType.bounce));
		} else {
			res.add(p.addMove(prevPos, newPos, moveType));
		}
		
		//
		// other special places
		//		
		switch(p.getPosition()) {
		case 6:
			// the bridge
			res.add(p.addMove(p.getPosition(), 12, MoveType.bridge));
			break;
			
		case 5:
		case 9:
		case 14:
		case 18:
		case 23:
		case 27:
			// the goose
			res.addAll(move(playerName, rollA, rollB, MoveType.goose));
			break;
		}
		
		//
		// Prank
		//
		
		for (Player otherPlayer : mapPlayers.values()) {
			if (p.getName().equals(otherPlayer.getName()))
				continue;
			
			if (otherPlayer.getPosition() == p.getPosition()) {
				res.add(otherPlayer.addMove(otherPlayer.getPosition(), prevPos, MoveType.prank));
			}
		}
		
		return res;
	}
	
	
	
	//
	// Getters
	// 
	
	public String getPlayersNames() {
		StringBuilder bld = new StringBuilder();
		for (String s : mapPlayers.keySet()) {
			if (bld.length() > 0)
				bld.append(", ");
			bld.append(s);
		}
		return bld.toString();
	}
	
	public Collection<Player> getPlayers() {
		return mapPlayers.values();
	}
	
	public GameStatus getStatus() {
		return status;
	}
	
	public boolean isFinished() {
		return status.equals(GameStatus.finished);
	}
	
}
