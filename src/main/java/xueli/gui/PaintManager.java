package xueli.gui;

import java.lang.ref.WeakReference;

import xueli.gui.driver.GraphicDriver;

public abstract class PaintManager {
	
	private final WeakReference<Widget> widget;

	private final GraphicDriver driver;

	public PaintManager(WeakReference<Widget> widget, GraphicDriver driver) {
		this.widget = widget;
		this.driver = driver;
		
	}

	public abstract void announceSizeChange();
	
	public abstract void announceRepaint(float x, float y, float width, float height);
	
	public abstract void doPaint();
	
	public abstract void release();
	
	public SizeHint measure() {
		var widget = getWidget();
		return widget.getSkin().measure(widget, driver);
	}
	
	public Widget getWidget() {
		return widget.get();
	}
	
	public GraphicDriver getDriver() {
		return driver;
	}
	
}
