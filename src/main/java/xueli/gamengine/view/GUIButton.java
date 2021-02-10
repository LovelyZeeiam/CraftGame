package xueli.gamengine.view;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import xueli.gamengine.utils.evalable.EvalableFloat;
import xueli.gamengine.utils.Display;

import static org.lwjgl.nanovg.NanoVG.*;

public class GUIButton extends ViewWidget {

	private static final int NUMBER1 = 3;
	private static final int NUMBER2 = 5;
	private static final int NUMBER3 = 13;
	protected static NVGPaint paint;
	public static NVGColor BLACK_COLOR = NVGColor.create();
	public static NVGColor GREY_COLOR = NVGColor.create();
	public static NVGColor ANOTHER_GREY_Color = NVGColor.create();
	public static NVGColor Darker_GREY_Color = NVGColor.create();

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

	public static void buttonBorder(long nvg, float x, float y, float width, float height) {
		nvgBeginPath(nvg);
		nvgMoveTo(nvg, x, y);
		nvgLineTo(nvg, x + width, y);
		nvgLineTo(nvg, x + width, y + height);
		nvgLineTo(nvg, x, y + height);
		nvgLineTo(nvg, x, y);
		nvgFillColor(nvg, BLACK_COLOR);
		nvgFill(nvg);

		nvgBeginPath(nvg);
		nvgMoveTo(nvg, x + NUMBER1, y + NUMBER1);
		nvgLineTo(nvg, x + width - NUMBER1, y + NUMBER1);
		nvgLineTo(nvg, x + width - NUMBER1, y + height - NUMBER1);
		nvgLineTo(nvg, x + NUMBER1, y + height - NUMBER1);
		nvgLineTo(nvg, x + NUMBER1, y + NUMBER1);
		nvgFillColor(nvg, GREY_COLOR);
		nvgFill(nvg);

		nvgBeginPath(nvg);
		nvgMoveTo(nvg, x + NUMBER2, y + NUMBER2);
		nvgLineTo(nvg, x + width - NUMBER2, y + NUMBER2);
		nvgLineTo(nvg, x + width - NUMBER2, y + height - NUMBER2);
		nvgLineTo(nvg, x + NUMBER2, y + height - NUMBER2);
		nvgLineTo(nvg, x + NUMBER2, y + NUMBER2);
		nvgFillColor(nvg, ANOTHER_GREY_Color);
		nvgFill(nvg);

		nvgBeginPath(nvg);
		nvgMoveTo(nvg, x + width, y + height);
		nvgLineTo(nvg, x, y + height);
		nvgLineTo(nvg, x, y + height - NUMBER3);
		nvgLineTo(nvg, x + width, y + height - NUMBER3);
		nvgLineTo(nvg, x + width, y + height);
		nvgFillColor(nvg, Darker_GREY_Color);
		nvgFill(nvg);

	}

	@Override
	public void draw(long nvg) {
		paint.clear();

		super.anim_tick();

		// 边框
		buttonBorder(nvg, x.getValue(), y.getValue(), width.getValue(), height.getValue());

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
