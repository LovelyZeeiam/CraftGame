package xueli.gui;

import xueli.gui.driver.GraphicDriver;

// So I admit that I glance at the source of JavaFX :}
// The skin only affects the widget itself, so it shouldn't be considered when laying out parent widget
public interface WidgetSkin {

	public void install(Widget widget);

	public SizeHint measure(Widget widget, GraphicDriver graphics);
	
	// Paint an area of this widget
	public void paint(Widget widget, float x, float y, float width, float height, GraphicDriver graphics);
	
	public void uninstall(Widget widget);

}
