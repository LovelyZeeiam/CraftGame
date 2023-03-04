package xueli.jrich2.widgets;

import java.awt.Dimension;

import xueli.jrich2.LayoutParams;
import xueli.jrich2.MeasureSpec;
import xueli.jrich2.RichConsole;
import xueli.jrich2.RichWidget;

public class RichText extends RichWidget {
	
	@SuppressWarnings("unused")
	private String text = "";
	
	public RichText(RichConsole ctx) {
		super(ctx);
	}
	
	public RichText(RichConsole ctx, LayoutParams layoutParams) {
		super(ctx, layoutParams);
	}

	@Override
	public Dimension onMeasure(MeasureSpec widthSpec, MeasureSpec heightSpec) {
		// TODO
		return null;
	}
	
}
