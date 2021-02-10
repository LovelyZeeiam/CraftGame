package xueli.gamengine.view;

import org.lwjgl.nanovg.NVGColor;
import xueli.gamengine.utils.evalable.EvalableFloat;

import java.util.Random;

public class GUIRandomTextView extends GUITextView {

	public GUIRandomTextView(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height,
			EvalableFloat textSize, NVGColor textColor, int align) {
		super(x, y, width, height, textSize, textColor, align);
	}

	public GUIRandomTextView(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height,
			EvalableFloat textSize, NVGColor textColor, int align, NVGColor borderColor, int border_width) {
		super(x, y, width, height, textSize, textColor, align, borderColor, border_width);
	}

	public void setText(String[] text) {
		int length = text.length;
		int random = (int) (new Random().nextFloat() * length);
		super.setText(text[random]);
	}

}
