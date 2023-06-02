package xueli.gui.layout;

import xueli.gui.SizeHint;
import xueli.gui.Widget;
import xueli.gui.WidgetGroup;

// The best layout is "manual" layout (wrong
public class AbsoluteLayout implements LayoutManager {

	@Override
	public void addWidget(Widget widget, WidgetGroup parent) {
	}

	@Override
	public void removeWidget(Widget widget, WidgetGroup parent) {
	}

	@Override
	public SizeHint measure(WidgetGroup parent) {
		return new SizeHint(SizeHint.POLICY_IGNORED, 0, 0);
	}

	@Override
	public void doLayout(WidgetGroup parent) {
	}

}
