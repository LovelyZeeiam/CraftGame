package xueli.gui.skin;

import xueli.gui.SizeHint;
import xueli.gui.Widget;
import xueli.gui.WidgetGroup;
import xueli.gui.WidgetSkin;
import xueli.gui.driver.GraphicDriver;

public class DefaultWidgetGroupSkin implements WidgetSkin {
	
	@Override
	public void install(Widget widget) {
//		WidgetGroup controller = (WidgetGroup) widget;
		
	}
	
	// The name of this method is from Android!
	@Override
	public SizeHint measure(Widget widget, GraphicDriver graphics) {
		WidgetGroup controller = (WidgetGroup) widget;
		return controller.getLayoutManager().measure(controller);
	}
	
	@Override
	public void paint(Widget widget, float x, float y, float width, float height, GraphicDriver graphics) {
		WidgetGroup controller = (WidgetGroup) widget;
		
		int count = controller.getWidgetCount();
		for(int i = 0; i < count; i++) {
			Widget child = controller.getWidgetFromIndex(i);
			child.doPaint();
		}
		
	}

	@Override
	public void uninstall(Widget widget) {
//		WidgetGroup controller = (WidgetGroup) widget;
		
	}
	
}
