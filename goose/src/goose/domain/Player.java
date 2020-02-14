package goose.domain;

import java.util.LinkedList;

public class Player {
	String name;	
	LinkedList<Move> moves = new LinkedList<>();
	
	int diceRolled;
	
	public Player(String name) {
		super();
		this.name = name;
		moves.add(new Move(0, 0, MoveType.start, this));
	}

	public Move addMove(int from, int to, MoveType type) {
		Move it = new Move(from, to, type, this);
		moves.add(it);
		return it;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumMoves() {
		return moves.size();
	}
	
	public int getPosition() {
		return moves.getLast().getTo();
	}
	
	public boolean winner() {
		return moves.getLast().getType().equals(MoveType.winner);
	}

	public int getDiceRolled() {
		return diceRolled;
	}
	public int incdiceRolled() {
		return diceRolled++;
	}
}
