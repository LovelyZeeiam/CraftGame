package xueli.game.utils.renderer.widgets;

import xueli.game.display.DisplayUtils;
import xueli.utils.eval.EvalableFloat;

public abstract class IWidget {

	protected EvalableFloat x, y, width, height;
	
	public IWidget(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}

	public abstract void stroke(long nvg);

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
