package xueli.gamengine.view.anim2d;

import org.lwjgl.nanovg.NVGColor;
import xueli.gamengine.utils.evalable.EvalableFloat;

import static org.lwjgl.nanovg.NanoVG.*;

public class ElementTextView extends Element2D {

	private String text;
	private EvalableFloat textSize;
	private NVGColor textColor;
	private int align;

	public ElementTextView(String name, NVGColor textColor, EvalableFloat size, EvalableFloat width,
			EvalableFloat height, int align, String text) {
		super(name, width, height);
		this.text = text;
		this.textColor = textColor;
		this.textSize = size;
		this.align = align;

	}

	@Override
	public void paint(long nvg) {
		nvgFontSize(nvg, textSize.getValue());
		nvgFontFace(nvg, "simhei");
		nvgTextAlign(nvg, align);
		nvgFillColor(nvg, textColor);
		nvgText(nvg, getWidth().getValue(), getHeight().getValue(), text);

	}

	@Override
	public void requireSized() {
		super.requireSized();
		textSize.needEvalAgain();

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "ElementTextView{" + "text='" + text + '\'' + ", textSize=" + textSize + ", textColor=" + textColor
				+ ", align=" + align + '}';
	}

}
