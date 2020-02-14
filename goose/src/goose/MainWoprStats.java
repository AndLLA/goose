package goose;

import goose.engine.ConsoleGameWoprStats;

public class MainWoprStats {
	public static void main(String[] args) throws Exception {
		long i = 1;
		while (true) {
			ConsoleGameWoprStats cg = new ConsoleGameWoprStats(i);
			cg.run();	
			
			System.out.println(i + "\t" + cg.getStats().toString());
			i++;
		}
	}
}
