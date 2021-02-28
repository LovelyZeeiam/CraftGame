package xueli.game.display;

import xueli.game.Game;

public class DisplayUtils {

	private DisplayUtils() {
	}

	public static boolean isMouseInBorder(float x, float y, float width, float height) {
		float cx = Game.INSTANCE_GAME.getCursorX();
		float cy = Game.INSTANCE_GAME.getCursorY();
		if (cx < x)
			return false;
		if (cx > x + width)
			return false;
		if (cy < y)
			return false;
		if (cy > y + height)
			return false;
		return true;
	}

}
