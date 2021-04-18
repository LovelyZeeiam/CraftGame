package xueli.game.renderer.widgets;

import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgRGBAf;
import static org.lwjgl.nanovg.NanoVG.nvgRect;

import java.text.MessageFormat;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;

import xueli.game.Game;
import xueli.game.utils.NVGColors;
import xueli.utils.eval.EvalableFloat;

@Deprecated
public class TextBox extends IWidget {

	private static EvalableFloat border = new EvalableFloat("1.5 * scale");

	private static NVGColor borderColor = NVGColors.BLACK;
	private static NVGColor borderChosenColor = NVGColor.create();
	
	static {
		nvgRGBAf(1.0f, 1.0f, 0.9f, 1.0f, borderChosenColor);
		
	}
	
	private String hint;

	private boolean isActive = false;
	
	private StringBuffer buffer = new StringBuffer();
	
	private int cursor = 0;
	private int line = 0;
	
	private EvalableFloat text_render_y;

	public TextBox(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height, String hint, long nvg) {
		super(x, y, width, height);
		
		this.hint = hint;
		if(this.hint == null) hint = "";
		
		this.text_render_y = new EvalableFloat(MessageFormat.format("({0}) + ({1}) / 2", y.getExpression(), height.getExpression()));
		
	}

	@Override
	public void stroke(long nvg, String fontName) {
		nvgBeginPath(nvg);
		nvgRect(nvg, x.getValue(), y.getValue(), width.getValue(), height.getValue());
		if (isActive)
			nvgFillColor(nvg, borderChosenColor);
		else
			nvgFillColor(nvg, borderColor);
		nvgFill(nvg);
		
		nvgBeginPath(nvg);
		nvgRect(nvg, x.getValue() + border.getValue(), y.getValue() + border.getValue(), width.getValue() - border.getValue() * 2, height.getValue() - border.getValue() * 2);
		nvgFillColor(nvg, NVGColors.DARK_GRAY);
		nvgFill(nvg);
		
		

	}

	@Override
	public void update() {
		if (Game.INSTANCE_GAME.getDisplay().isMouseDownOnce(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			this.isActive = isMouseHover();
			
		}
		
		if(this.isActive) {
			if(Game.INSTANCE_GAME.getDisplay().isKeyDownOnce(GLFW.GLFW_KEY_LEFT))
				cursor--;
			if(Game.INSTANCE_GAME.getDisplay().isKeyDownOnce(GLFW.GLFW_KEY_RIGHT))
				cursor++;
			cursor = Math.min(buffer.length(), cursor);
			cursor = Math.max(0, cursor);
			
		}
		

	}
	
	public void setValue(String string) {
		this.buffer = new StringBuffer(string);
		
	}
	
	public String getValue() {
		return this.buffer.toString();
	}
	
	private void calculateTextRows() {
		
		
	}
	
	@Override
	public void size() {
		super.size();
		
		border.needEvalAgain();
		text_render_y.needEvalAgain();
		
	}

}
