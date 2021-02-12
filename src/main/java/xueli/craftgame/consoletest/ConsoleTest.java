package xueli.craftgame.consoletest;

import xueli.gamengine.utils.Logger;

import java.util.ArrayList;

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
