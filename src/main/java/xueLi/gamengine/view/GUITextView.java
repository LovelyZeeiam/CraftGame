package xueLi.gamengine.view;

import org.lwjgl.nanovg.NVGColor;

import xueLi.gamengine.utils.EvalableFloat;

import static org.lwjgl.nanovg.NanoVG.*;

public class GUITextView extends ViewWidget {

	private EvalableFloat textSize;
	private NVGColor textColor;
	private String text;
	private int align;

	public GUITextView(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height,
			EvalableFloat textSize, NVGColor textColor, int align) {
		super(x, y, width, height);
		this.textSize = textSize;
		this.textColor = textColor;
		this.align = align;

	}

	public GUITextView(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height,
			EvalableFloat textSize, NVGColor textColor, int align, NVGColor borderColor, int border_width) {
		super(x, y, width, height, borderColor, border_width);
		this.textSize = textSize;
		this.textColor = textColor;
		this.align = align;

	}

	public GUITextView(float real_x, float real_y, float real_width, float real_height, EvalableFloat textSize,
			NVGColor textColor, String text, int align) {
		super(real_x, real_y, real_width, real_height);
		this.textSize = textSize;
		this.textColor = textColor;
		this.text = text;
		this.align = align;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;

	}

	@Override
	public void draw(long nvg) {
		super.anim_tick();
		super.drawBorder(nvg);

		nvgFontSize(nvg, textSize.value);
		nvgFontFace(nvg, "simhei");
		nvgTextAlign(nvg, align);
		nvgFillColor(nvg, textColor);
		nvgText(nvg, real_x, real_y, text);

	}

	@Override
	public void size() {
		super.size();
		textSize.eval();

	}

}
