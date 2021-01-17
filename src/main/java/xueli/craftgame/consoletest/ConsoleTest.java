package xueli.craftgame.consoletest;

import java.util.ArrayList;

import xueli.gamengine.utils.Logger;

public class ConsoleTest implements Runnable {

	private ArrayList<Runnable> tests = new ArrayList<>();

	public static void main(String[] args) {
		Logger.info("Welcome to console test~");
		new ConsoleTest().run();

	}

	@Override
	public void run() {

		new WorldGenerationTest().run();

	}

}
