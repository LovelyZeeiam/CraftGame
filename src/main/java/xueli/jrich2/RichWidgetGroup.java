package xueli.jrich2;

import java.awt.Dimension;
import java.util.ArrayList;

import xueli.jrich2.MeasureSpec.Mode;

public class RichWidgetGroup extends RichWidget {

	private ArrayList<RichWidget> widgets = new ArrayList<>();
	
	public RichWidgetGroup(RichConsole ctx) {
		super(ctx);
	}
	
	public RichWidgetGroup(RichConsole ctx, LayoutParams layoutParams) {
		super(ctx, layoutParams);
	}
	
	public int getWidgetCount() {
		return widgets.size();
	}
	
	public RichWidget getWidgetAt(int index) {
		return widgets.get(index);
	}
	
	public int addWidget(RichWidget widget) {
		int id = widgets.size();
		widgets.add(widget);
		return id;
	}
	
	/**
	 * Actually this is a vertical flow layout
	 * TODO: Can we directly get the bounding rectangle of the widget when measuring?
	 */
	@Override
	public Dimension onMeasure(MeasureSpec widthSpec, MeasureSpec heightSpec) {
		Dimension dim = new Dimension();
		boolean widthNeedCalc = widthSpec.mode != Mode.EXACTLY, heightNeedCalc = heightSpec.mode != Mode.EXACTLY;		
		
		if(widthNeedCalc || heightNeedCalc) {
			int height = 0;
			
			for (RichWidget widget : widgets) {
				LayoutParams wParams = widget.getLayoutParams();
				Dimension wSize = widget.onMeasure(getMeasureSpec(wParams.getWidth(), widthSpec), getMeasureSpec(wParams.getHeight(), heightSpec));
				
				MLayoutParams mParams = new MLayoutParams(wParams);
				mParams.mTop = height;
				mParams.mLeft = 0;
				mParams.mRight = wSize.width;
				height += wSize.height;
				mParams.mBottom = height;
				widget.setLayoutParamsForDraw(mParams);
				
			}
			dim.height = height;
			
			if(!widthNeedCalc) {
				dim.width = widthSpec.value;
			}
			if(!heightNeedCalc) {
				dim.height = heightSpec.value;
			}
			
		} else {
			dim.width = widthSpec.value;
			dim.height = heightSpec.value;
		}
		
		return dim;
	}
	
	private MeasureSpec getMeasureSpec(int sizeInParam, MeasureSpec parentSpec) {
		if(sizeInParam == LayoutParams.FILL_PARENT) {
			return switch(parentSpec.mode) {
				case EXACTLY -> new MeasureSpec(Mode.EXACTLY, parentSpec.value);
				case MAX_LIMITED -> new MeasureSpec(Mode.MAX_LIMITED, parentSpec.value);
				case UNLIMITED -> new MeasureSpec(Mode.UNLIMITED, 0);
			};
		} else if(sizeInParam > 0) {
			return new MeasureSpec(Mode.EXACTLY, sizeInParam);
		}
		
		// When SizeInParam is JUST_WRAP_CONTENT or other invalid value
		if(parentSpec.mode == Mode.UNLIMITED) {
			return new MeasureSpec(Mode.UNLIMITED, 0);
		} else {
			return new MeasureSpec(Mode.MAX_LIMITED, parentSpec.value);
		}
	}
	
	@Override
	public void onDraw(int left, int top, int right, int bottom, Canvas canvas) {
		for (RichWidget widget : widgets) {
		 	MLayoutParams mParams = (MLayoutParams) widget.getLayoutParamsForDraw();
			widget.onDraw(mParams.mLeft, mParams.mTop, mParams.mRight, mParams.mBottom, canvas);
		}
	}
	
	public static class MLayoutParams extends LayoutParams {
		
		private int mLeft, mTop, mRight, mBottom;

		public MLayoutParams(LayoutParams p) {
			super(p);
		}
		
	}
	
}
