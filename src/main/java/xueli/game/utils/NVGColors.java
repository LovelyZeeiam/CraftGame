package xueli.game.utils;

import static org.lwjgl.nanovg.NanoVG.nvgRGB;
import static org.lwjgl.nanovg.NanoVG.nvgRGBA;
import static org.lwjgl.nanovg.NanoVG.nvgRGBf;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.util.vector.Vector3f;

import xueli.utils.WindowUtils;

public class NVGColors {

	public static NVGColor GREEN = NVGColor.create();
	public static NVGColor BLUE = NVGColor.create();
	public static NVGColor WHITE = NVGColor.create();
	public static NVGColor BLACK = NVGColor.create();
	public static NVGColor YELLOW = NVGColor.create();
	public static NVGColor PURPLE = NVGColor.create();
	public static NVGColor TRANSPARENT_BLACK = NVGColor.create();

	// Only when Windows 10
	public static NVGColor WINDOW_THEME_COLOR = NVGColor.create();

	static {
		nvgRGB((byte) 0, (byte) 255, (byte) 0, GREEN);
		nvgRGB((byte) 0, (byte) 0, (byte) 255, BLUE);
		nvgRGB((byte) 255, (byte) 255, (byte) 255, WHITE);
		nvgRGB((byte) 0, (byte) 0, (byte) 0, BLACK);
		nvgRGB((byte) 255, (byte) 255, (byte) 0, YELLOW);
		nvgRGB((byte) 128, (byte) 0, (byte) 128, PURPLE);
		nvgRGBA((byte) 0, (byte) 0, (byte) 0, (byte) 128, TRANSPARENT_BLACK);

		Vector3f themeColor = WindowUtils.getSystemThemeColor(new Vector3f(1, 1, 1));
		nvgRGBf(themeColor.x, themeColor.y, themeColor.z, WINDOW_THEME_COLOR);

	}
}
