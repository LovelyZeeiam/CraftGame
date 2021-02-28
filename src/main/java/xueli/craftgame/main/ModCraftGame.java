package xueli.craftgame.main;

import xueli.craftgame.main.state.StateSplash;
import xueli.game.Game;
import xueli.game.modding.IMod;

public class ModCraftGame extends IMod {

	public static ModCraftGame MAIN_GAME;

	private boolean inWorld = false;

	public ModCraftGame(String workingDirectory) {
		super("CraftGame", "res/", workingDirectory);
		MAIN_GAME = this;

	}

	@Override
	public void onInit() {
		Game.INSTANCE_GAME.getManager().setCurrentRenderer(new StateSplash());

	}

	@Override
	public void onTick() {

	}

	@Override
	public void onRelease() {

	}

	public boolean isInWorld() {
		return inWorld;
	}
}
