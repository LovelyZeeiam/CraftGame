package xueli.mcremake.client;

import org.lwjgl.system.Library;

public class CraftGameMain {

	public static void main(String[] args) {
		Library.initialize();
		new CraftGameClient().run();

	}

}
