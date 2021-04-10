package xueli.game.renderer.widgets;

import xueli.game.display.DisplayUtils;
import xueli.utils.eval.EvalableFloat;

public class Boundary {
	
	protected EvalableFloat x, y, width, height;
	
	public Boundary(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

	}
	
	public Boundary(Integer x, Integer y, Float width, Float height) {
		this.x = new EvalableFloat(x.toString());
		this.y = new EvalableFloat(y.toString());
		this.width = new EvalableFloat(width.toString());
		this.height = new EvalableFloat(height.toString());
				
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
	
	public void size() {
		x.needEvalAgain();
		y.needEvalAgain();
		width.needEvalAgain();
		height.needEvalAgain();
	}
	
	public boolean isMouseHover() {
		return DisplayUtils.isMouseInBorder(x.getValue(), y.getValue(), width.getValue(), height.getValue());
	}

}
