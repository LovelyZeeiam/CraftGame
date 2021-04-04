package xueli.game.renderer.widgets;

import xueli.game.display.DisplayUtils;
import xueli.utils.eval.EvalableFloat;

public class IWidget {

	protected EvalableFloat x, y, width, height;
	
	public IWidget() {}
	
	public IWidget(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
	
	public EvalableFloat getX() {
		return x;
	}

	public void setX(EvalableFloat x) {
		this.x = x;
	}

	public EvalableFloat getY() {
		return y;
	}

	public void setY(EvalableFloat y) {
		this.y = y;
	}

	public EvalableFloat getWidth() {
		return width;
	}

	public void setWidth(EvalableFloat width) {
		this.width = width;
	}

	public EvalableFloat getHeight() {
		return height;
	}

	public void setHeight(EvalableFloat height) {
		this.height = height;
	}

	public void stroke(long nvg, String fontName) {}

	public void size(int w, int h) {
		x.needEvalAgain();
		y.needEvalAgain();
		width.needEvalAgain();
		height.needEvalAgain();
		
	}
	
	public boolean isMouseHover() {
		return DisplayUtils.isMouseInBorder(x.getValue(), y.getValue(), width.getValue(), height.getValue());
	}
	
}
