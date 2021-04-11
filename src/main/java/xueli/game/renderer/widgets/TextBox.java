package xueli.game.renderer.widgets;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;

import xueli.game.Game;
import xueli.game.utils.NVGColors;
import xueli.utils.eval.EvalableFloat;

import static org.lwjgl.nanovg.NanoVG.*;

public class TextBox extends IWidget {

	private static EvalableFloat border = new EvalableFloat("2.0 * scale");

	private static NVGColor borderColor = NVGColors.WHITE, borderChosenColor = NVGColors.PURPLE;

	private String hint;

	private boolean isActive = false;
	private StringBuffer buffer = new StringBuffer();

	public TextBox(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height, String hint) {
		super(x, y, width, height);
		this.hint = hint;

	}

	@Override
	public void stroke(long nvg, String fontName) {
		nvgBeginPath(nvg);
		nvgRect(nvg, x.getValue(), y.getValue(), width.getValue(), height.getValue());
		if (isActive)
			nvgFillColor(nvg, borderColor);
		else
			nvgFillColor(nvg, borderChosenColor);
		nvgFill(nvg);

	}

	@Override
	public void update() {
		if (Game.INSTANCE_GAME.getDisplay().isMouseDownOnce(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			this.isActive = isMouseHover();

		}

	}

}
