package xueli.gamengine.view.anim2d;

import xueli.gamengine.utils.evalable.EvalableFloat;

public abstract class Element2D {

	private String name;
	private EvalableFloat width, height;

	public Element2D(String name, EvalableFloat width, EvalableFloat height) {
		this.name = name;
		this.width = width;
		this.height = height;

	}

	public abstract void paint(long nvg);

	public void requireSized() {
		width.needEvalAgain();
		height.needEvalAgain();

	}

	public String getName() {
		return name;
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

}
