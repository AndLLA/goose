package goose;

import goose.engine.ConsoleGameWopr;

public class MainWopr {
	public static void main(String[] args) throws Exception {
		long i = 1;
		while (true) {
			ConsoleGameWopr cg = new ConsoleGameWopr(i++);
			cg.run();			
		}
	}
}
