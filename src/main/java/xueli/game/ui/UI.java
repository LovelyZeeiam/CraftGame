package xueli.game.ui;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import static org.lwjgl.nanovg.NanoVG.*;

public class UI {

	private static NVGPaint paint = NVGPaint.create();

	private UI() {
	}

	public static void drawTexture(long nvg, float x, float y, float width, float height, int image) {
		nvgImagePattern(nvg, x, y, width, height, 0, image, 1.0f, paint);
		nvgBeginPath(nvg);
		nvgRect(nvg, x, y, width, height);
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);
	}

	public static void drawRect(long nvg, float x, float y, float width, float height, NVGColor color) {
		nvgBeginPath(nvg);
		nvgRect(nvg, x, y, width, height);
		nvgFillColor(nvg, color);
		nvgFill(nvg);
	}

}
