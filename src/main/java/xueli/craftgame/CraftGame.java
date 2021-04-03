package xueli.craftgame;

import org.lwjgl.opengl.GL11;

import xueli.craftgame.state.StateSplash;
import xueli.game.Game;
import xueli.game.player.PlayerStat;
import xueli.utils.io.Log;

public class CraftGame extends Game {

	public boolean initComplete = true;

	private PlayerStat player = new PlayerStat();

	public CraftGame() {
		super(800, 600, "CraftGame");

	}

	@Override
	public void oncreate() {
		Log.logger.finer("[Player]" + player.toString());

		this.rendererManager.setCurrentRenderer(new StateSplash());

	}

	@Override
	public void ontick() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

		rendererManager.render();

	}

	@Override
	public void onrelease() {
		rendererManager.release();

	}

	public PlayerStat getPlayer() {
		return player;
	}

}
