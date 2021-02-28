package xueli.craftgame;

import org.lwjgl.opengl.GL11;

import xueli.craftgame.main.ModCraftGame;
import xueli.game.Game;
import xueli.game.modding.ModManager;

public class CraftGame extends Game {

	private ModManager mods = new ModManager();

	public CraftGame() {
		super(800, 600, "CraftGame", "./.cg/");

	}

	@Override
	public void oncreate() {
		// register mods
		mods.register(new ModCraftGame(this.workingDirectory));

	}

	@Override
	public void ontick() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		mods.tick();

	}

	@Override
	public void onrelease() {
		mods.release();

	}

}
