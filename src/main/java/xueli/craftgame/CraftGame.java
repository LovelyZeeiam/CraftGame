package xueli.craftgame;

import org.lwjgl.opengl.GL11;

import xueli.craftgame.main.ModCraftGame;
import xueli.game.Game;
import xueli.game.modding.ModManager;
import xueli.game.player.PlayerStat;
import xueli.utils.io.Log;

public class CraftGame extends Game {

	private ModManager mods = new ModManager();
	public boolean initComplete = false;

	private PlayerStat player = new PlayerStat();

	public CraftGame() {
		super(800, 600, "CraftGame", "./.cg/");

	}

	@Override
	public void oncreate() {
		Log.logger.finer("[Player]" + player.toString());

		// register main mod
		mods.register(new ModCraftGame(this.workingDirectory));

		installMods();

	}

	public void installMods() {
		// for launcher to add install mods code

		// at last we need this
		addTaskForMainThread(() -> this.initComplete = true);

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

	public PlayerStat getPlayer() {
		return player;
	}

}
