package xueli.craftgame;

import org.lwjgl.opengl.GL11;

import xueli.craftgame.state.StateWorld;
import xueli.game.Game;
import xueli.utils.io.Files;

public class WorldMain extends Game {

	public WorldMain() {
		super(800, 600, "CraftGame - World");

	}

	@Override
	public void oncreate() {
		System.setProperty("java.util.logging.config.file", Files.getResourcePackedInJar("logging.properties").getPath());
		
		rendererManager.setCurrentRenderer(new StateWorld());

	}

	@Override
	public void ontick() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glClearColor(0.7f, 0.7f, 0.9f, 1.0f);

	}

	@Override
	public void onrelease() {
		
	}

	public static void main(String[] args) {
		new Thread(new WorldMain()).start();

	}

}
