package goose;

import goose.engine.ConsoleGameWopr;

public class MainWoprOnce {
	public static void main(String[] args) throws Exception {
		
		// 1 player - 4 rolls
		new ConsoleGameWopr(1953).run();
		
		// 1 player - 159 rolls
		new ConsoleGameWopr(388691).run();
		
		// 9 players - 4 rolls 
		new ConsoleGameWopr(1263).run();
		
		// 9 players - 24 rolls 
		new ConsoleGameWopr(1483679).run();
		
		/*
			  numPlayers minDicesRolled maxDicesRolled minSeed maxSeed
			1          1              4            159    1953  388691
			2          2              4             87     634  641197
			3          3              4             55     362  941110
			4          4              4             43    2437  181514
			5          5              4             34     458  530396
			6          6              4             31     157  179609
			7          7              4             28    1351  928829
			8          8              4             24     652  606235
			9          9              4             24    1263 1483679
		 */
		
	}
}
