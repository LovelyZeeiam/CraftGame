package xueli.gamengine.view;

import org.lwjgl.nanovg.NVGColor;

import static org.lwjgl.nanovg.NanoVG.nvgRGB;
import static org.lwjgl.nanovg.NanoVG.nvgRGBA;

public class GuiColor {

	public static NVGColor GREEN = NVGColor.create();
	public static NVGColor BLUE = NVGColor.create();
	public static NVGColor WHITE = NVGColor.create();
	public static NVGColor BLACK = NVGColor.create();
	public static NVGColor YELLOW = NVGColor.create();
	public static NVGColor PURPLE = NVGColor.create();
	public static NVGColor TRANSPARENT_BLACK = NVGColor.create();

	static {
		nvgRGB((byte) 0, (byte) 255, (byte) 0, GREEN);
		nvgRGB((byte) 0, (byte) 0, (byte) 255, BLUE);
		nvgRGB((byte) 255, (byte) 255, (byte) 255, WHITE);
		nvgRGB((byte) 0, (byte) 0, (byte) 0, BLACK);
		nvgRGB((byte) 255, (byte) 255, (byte) 0, YELLOW);
		nvgRGB((byte) 128, (byte) 0, (byte) 128, PURPLE);
		nvgRGBA((byte) 0, (byte) 0, (byte) 0, (byte) 128, TRANSPARENT_BLACK);

	}
}
