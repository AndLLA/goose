package goose;

import goose.engine.ConsoleGameWopr;

public class MainWoprOnce {
	public static void main(String[] args) throws Exception {
		
		// 1 player - 4 rolls
		new ConsoleGameWopr(1953).run();
		
		// 1 player - 210 rolls
		new ConsoleGameWopr(13499664).run();
		
		// 1 player - 159 rolls
		new ConsoleGameWopr(388691).run();
		
		// 9 players - 4 rolls 
		new ConsoleGameWopr(1263).run();
		
		// 9 players - 27 rolls 
		new ConsoleGameWopr(14982468).run();
		
		/*
			  numPlayers minDicesRolled maxDicesRolled maxDicesRolledTot minSeed  maxSeed
			1          1              4            210               210    1953 13499664
			2          2              4             98               196     634 11144320
			3          3              4             62               186     362  5942340
			4          4              4             46               184    2437  9304955
			5          5              4             39               195     458  8619631
			6          6              4             35               210     157  6252911
			7          7              4             29               203    1351 10461846
			8          8              4             27               216     652 16463519
			9          9              4             27               243    1263 14982468
		 */
		
	}
}
