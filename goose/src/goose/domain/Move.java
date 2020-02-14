package goose.domain;

public class Move {
	int from;
	int to;
	MoveType type;
	Player player;
	
	public Move(int from, int to, MoveType type, Player player) {
		this.from = from;
		this.to = to;
		this.type = type;
		this.player = player;
	}
	
	public int getFrom() {
		return from;
	}
	public int getTo() {
		return to;
	}
	public Player getPlayer() {
		return player;
	}

	public MoveType getType() {
		return type;
	}
	
}
