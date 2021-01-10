package xueli.gamengine.view;

import static org.lwjgl.nanovg.NanoVG.nvgRGB;

import org.lwjgl.nanovg.NVGColor;

public class GuiColor {

	public static NVGColor WHITE = NVGColor.create();
	public static NVGColor BLACK = NVGColor.create();
	public static NVGColor YELLOW = NVGColor.create();
	public static NVGColor PURPLE = NVGColor.create();

	static {
		nvgRGB((byte) 255, (byte) 255, (byte) 255, WHITE);
		nvgRGB((byte) 0, (byte) 0, (byte) 0, BLACK);
		nvgRGB((byte) 255, (byte) 255, (byte) 0, YELLOW);
		nvgRGB((byte) 128, (byte) 0, (byte) 128, PURPLE);

	}
}
