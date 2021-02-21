package xueli.craftgame.net.client;

import xueli.craftgame.CraftGame;
import xueli.gamengine.utils.Display;
import xueli.gamengine.utils.Time;

public class PlayerPickerTimer {

	public static final long PLACE_DURATION = 200;

	private long lastPlaceTime = 0;

	private Display display;

	public PlayerPickerTimer() {
		this.display = CraftGame.INSTANCE_CRAFT_GAME.getDisplay();

	}

	public void tick() {
		boolean isMouseButtonDown = display.isMouseDown(0) || display.isMouseDown(1);

		if (!isMouseButtonDown) {
			this.lastPlaceTime = 0;
			return;
		}

		if (Time.thisTime - lastPlaceTime > PLACE_DURATION) {
			lastPlaceTime = Time.thisTime;

		}

	}

	public boolean canPlaceBlock() {
		return Time.thisTime - lastPlaceTime > PLACE_DURATION;
	}

}
