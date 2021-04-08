package xueli.game.renderer.widgets;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgRGBAf;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;

import org.lwjgl.nanovg.NVGColor;

import xueli.game.utils.NVGColors;
import xueli.utils.eval.EvalableFloat;

public class Button extends IWidget {

	private static NVGColor button_text_color_hover = NVGColor.create();
	private static NVGColor button_text_color = NVGColor.create();

	private static NVGColor button_color = NVGColor.create();
	private static NVGColor button_color_disable = NVGColor.create();

	private static NVGColor gray = NVGColor.create();

	private static EvalableFloat button_border_width = new EvalableFloat("2.0 * scale");

	static {
		nvgRGBAf(1, 1, 0.5f, 1, button_text_color_hover);
		nvgRGBAf(0.9f, 0.9f, 0.9f, 1, button_text_color);
		nvgRGBAf(0.35f, 0.35f, 0.35f, 0.85f, button_color);
		nvgRGBAf(0.2f, 0.2f, 0.2f, 0.85f, button_color_disable);
		nvgRGBAf(0.9f, 0.9f, 0.9f, 0.4f, gray);

	}

	private boolean enable;

	private EvalableFloat fontSize;
	private String text;

	public Button(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height, String text, EvalableFloat fontSize, boolean enable) {
		super(x, y, width, height);
		this.enable = enable;
		this.text = text;
		this.fontSize = fontSize;
		this.enable = enable;

	}

	public void stroke(long nvg, String fontName) {
		boolean isHover = isMouseHover();

		nvgBeginPath(nvg);
		nvgRect(nvg, x.getValue() - button_border_width.getValue(), y.getValue() - button_border_width.getValue(),
				width.getValue() + 2 * button_border_width.getValue(),
				height.getValue() + 2 * button_border_width.getValue());
		if (enable && isHover)
			nvgFillColor(nvg, gray);
		else
			nvgFillColor(nvg, NVGColors.BLACK);
		nvgFill(nvg);

		nvgBeginPath(nvg);
		nvgRect(nvg, x.getValue(), y.getValue(), width.getValue(), height.getValue());
		if (enable)
			nvgFillColor(nvg, button_color);
		else
			nvgFillColor(nvg, button_color_disable);
		nvgFill(nvg);

		nvgFontSize(nvg, fontSize.getValue());
		nvgFontFace(nvg, fontName);
		nvgTextAlign(nvg, NVG_ALIGN_MIDDLE | NVG_ALIGN_CENTER);
		if (enable && isHover)
			nvgFillColor(nvg, button_text_color_hover);
		else
			nvgFillColor(nvg, button_text_color);
		nvgText(nvg, x.getValue() + width.getValue() / 2, y.getValue() + height.getValue() / 2, text);

	}

	public void size(int w, int h) {
		super.size(w, h);

		button_border_width.needEvalAgain();

	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean isEnable() {
		return enable;
	}
	
	public boolean canBePressed() {
		return enable && isMouseHover();
	}

}
