package xueli.gui.layout;

import xueli.gui.SizeHint;
import xueli.gui.Widget;
import xueli.gui.WidgetGroup;

public interface LayoutManager {
	
	public void addWidget(Widget widget, WidgetGroup parent);
	
	public void removeWidget(Widget widget, WidgetGroup parent);
	
	public SizeHint measure(WidgetGroup parent);
	
	public void doLayout(WidgetGroup parent);
	
}
