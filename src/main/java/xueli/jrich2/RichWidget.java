package xueli.jrich2;

import java.awt.Dimension;

public class RichWidget {
	
	protected final RichConsole ctx;
	private LayoutParams layoutParams, drawLayoutParams;
	
	public RichWidget(RichConsole ctx) {
		this.ctx = ctx;
		this.layoutParams = new LayoutParams(LayoutParams.JUST_WRAP_CONTENT, LayoutParams.JUST_WRAP_CONTENT);
	}
	
	public RichWidget(RichConsole ctx, LayoutParams layoutParams) {
		this.ctx = ctx;
		this.layoutParams = layoutParams;
	}

	/**
	 * Measure the widget and specify its own size
	 * @return The size of the widget
	 */
	public Dimension onMeasure(MeasureSpec widthSpec, MeasureSpec heightSpec) {
		return new Dimension();
	}
	
	/**
	 * Notify the widget that its position is determined and draw itself
	 */
	public void onDraw(int left, int top, int right, int bottom, Canvas canvas) {}

	public LayoutParams getLayoutParams() {
		return layoutParams;
	}

	public void setLayoutParams(LayoutParams layoutParams) {
		this.layoutParams = layoutParams;
	}
	
	public LayoutParams getLayoutParamsForDraw() {
		return drawLayoutParams;
	}
	
	public void setLayoutParamsForDraw(LayoutParams drawLayoutParams) {
		this.drawLayoutParams = drawLayoutParams;
	}
	
}
