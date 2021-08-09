package xueli.craftgame;

import org.lwjgl.opengl.GL11;

import xueli.craftgame.state.StateWorld;
import xueli.game.Game;

public class WorldMain extends Game {

	public WorldMain() {
		super(800, 600, "CraftGame - World");

	}

	@Override
	public void onCreate() {
		rendererManager.setCurrentRenderer(new StateWorld());

	}

	@Override
	public void onTick() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

	}

	@Override
	public void onRelease() {

	}

	public static void main(String[] args) {
		new Thread(new WorldMain()).start();

	}

}
