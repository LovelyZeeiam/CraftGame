package xueli.gamengine.view;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgLineTo;
import static org.lwjgl.nanovg.NanoVG.nvgMoveTo;
import static org.lwjgl.nanovg.NanoVG.nvgRGB;
import static org.lwjgl.nanovg.NanoVG.nvgRGBA;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import xueli.gamengine.utils.Display;
import xueli.gamengine.utils.EvalableFloat;

public class GUIButton extends ViewWidget {

	private static final int NUMBER1 = 3;
	private static final int NUMBER2 = 5;
	private static final int NUMBER3 = 13;
	protected static NVGPaint paint;
	private static NVGColor BLACK_COLOR = NVGColor.create();
	private static NVGColor GREY_COLOR = NVGColor.create();
	private static NVGColor ANOTHER_GREY_Color = NVGColor.create();
	private static NVGColor Darker_GREY_Color = NVGColor.create();

	static {
		paint = NVGPaint.create();

	}

	static {
		nvgRGB((byte) 0x0, (byte) 0x0, (byte) 0x0, BLACK_COLOR);
		nvgRGB((byte) 0xAAAAAA, (byte) 0xAAAAAA, (byte) 0xAAAAAA, GREY_COLOR);
		nvgRGB((byte) 0x6F6F6F, (byte) 0x6F6F6F, (byte) 0x6F6F6F, ANOTHER_GREY_Color);
		nvgRGBA((byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x415411, Darker_GREY_Color);

	}

	protected int textureID;
	private String labelString = null;
	private EvalableFloat textSize;
	private NVGColor textColor;
	private NVGColor clickedTextColor;

	public GUIButton(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height, String label,
			EvalableFloat textSize, NVGColor textColor, NVGColor borderColor, int borderWidth,
			NVGColor clickedTextColor) {
		super(x, y, width, height, borderColor, borderWidth);
		this.labelString = label;
		this.textSize = textSize;
		this.clickedTextColor = clickedTextColor;
		this.textColor = textColor;

		if (textSize != null) {
			textSize.needEvalAgain();
		}

	}

	@Override
	public void draw(long nvg) {
		paint.clear();

		super.anim_tick();

		// 边框
		nvgBeginPath(nvg);
		nvgMoveTo(nvg, x.getValue(), y.getValue());
		nvgLineTo(nvg, x.getValue() + width.getValue(), y.getValue());
		nvgLineTo(nvg, x.getValue() + width.getValue(), y.getValue() + height.getValue());
		nvgLineTo(nvg, x.getValue(), y.getValue() + height.getValue());
		nvgLineTo(nvg, x.getValue(), y.getValue());
		nvgFillColor(nvg, BLACK_COLOR);
		nvgFill(nvg);

		nvgBeginPath(nvg);
		nvgMoveTo(nvg, x.getValue() + NUMBER1, y.getValue() + NUMBER1);
		nvgLineTo(nvg, x.getValue() + width.getValue() - NUMBER1, y.getValue() + NUMBER1);
		nvgLineTo(nvg, x.getValue() + width.getValue() - NUMBER1, y.getValue() + height.getValue() - NUMBER1);
		nvgLineTo(nvg, x.getValue() + NUMBER1, y.getValue() + height.getValue() - NUMBER1);
		nvgLineTo(nvg, x.getValue() + NUMBER1, y.getValue() + NUMBER1);
		nvgFillColor(nvg, GREY_COLOR);
		nvgFill(nvg);

		nvgBeginPath(nvg);
		nvgMoveTo(nvg, x.getValue() + NUMBER2, y.getValue() + NUMBER2);
		nvgLineTo(nvg, x.getValue() + width.getValue() - NUMBER2, y.getValue() + NUMBER2);
		nvgLineTo(nvg, x.getValue() + width.getValue() - NUMBER2, y.getValue() + height.getValue() - NUMBER2);
		nvgLineTo(nvg, x.getValue() + NUMBER2, y.getValue() + height.getValue() - NUMBER2);
		nvgLineTo(nvg, x.getValue() + NUMBER2, y.getValue() + NUMBER2);
		nvgFillColor(nvg, ANOTHER_GREY_Color);
		nvgFill(nvg);

		nvgBeginPath(nvg);
		nvgMoveTo(nvg, x.getValue() + width.getValue(), y.getValue() + height.getValue());
		nvgLineTo(nvg, x.getValue(), y.getValue() + height.getValue());
		nvgLineTo(nvg, x.getValue(), y.getValue() + height.getValue() - NUMBER3);
		nvgLineTo(nvg, x.getValue() + width.getValue(), y.getValue() + height.getValue() - NUMBER3);
		nvgLineTo(nvg, x.getValue() + width.getValue(), y.getValue() + height.getValue());
		nvgFillColor(nvg, Darker_GREY_Color);
		nvgFill(nvg);

		int cursorX = Display.currentDisplay.getMouseX();
		int cursorY = Display.currentDisplay.getMouseY();

		if (isMouseHover()) {
			super.drawBorder(nvg);
			nvgFillColor(nvg, clickedTextColor);

		} else {
			nvgFillColor(nvg, textColor);

		}

		nvgFontSize(nvg, textSize.getValue());
		nvgFontFace(nvg, "simhei");
		nvgTextAlign(nvg, NVG_ALIGN_CENTER);
		nvgText(nvg, x.getValue() + width.getValue() / 2,
				y.getValue() + height.getValue() / 2 + textSize.getValue() / 4, labelString);

	}

	@Override
	public void size() {
		textSize.needEvalAgain();
		super.size();
	}

}
