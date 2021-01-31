package xueli.gamengine.view;

import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRect;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import xueli.gamengine.utils.EvalableFloat;

public class GUIBackground extends ViewWidget {

	private static NVGPaint paint = NVGPaint.create();
	private NVGColor backgroundColor = null;

	private int backgroundTexture = 0;
	private int texture_start_x, texture_start_y, texture_end_x, texture_end_y;

	public GUIBackground(NVGColor backgroundColor) {
		super(new EvalableFloat("0.0"), new EvalableFloat("0.0"), new EvalableFloat("win_width + 0.0"),
				new EvalableFloat("win_height + 0.0"));
		this.backgroundColor = backgroundColor;

	}

	public GUIBackground(int backgroundTexture, int texture_start_x, int texture_start_y, int texture_end_x,
			int texture_end_y) {
		super(new EvalableFloat("0.0"), new EvalableFloat("0.0"), new EvalableFloat("win_width + 0.0"),
				new EvalableFloat("win_height + 0.0"));
		this.backgroundTexture = backgroundTexture;

		this.texture_start_x = texture_start_x;
		this.texture_start_y = texture_start_y;
		this.texture_end_x = texture_end_x;
		this.texture_end_y = texture_end_y;

	}

	@Override
	public void draw(long nvg) {
		nvgBeginPath(nvg);
		nvgRect(nvg, 0, 0, width.getValue(), height.getValue());
		if (this.backgroundColor != null)
			nvgFillColor(nvg, backgroundColor);
		if (this.backgroundTexture != 0) {
			nvgImagePattern(nvg, texture_start_x, texture_start_y, texture_end_x, texture_end_y, 0, backgroundTexture,
					1.0f, paint);
			nvgFillPaint(nvg, paint);
		}
		nvgFill(nvg);

	}

}
