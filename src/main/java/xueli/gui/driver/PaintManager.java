package xueli.gui.driver;

import java.lang.ref.WeakReference;

import xueli.gui.Widget;

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
	
	public Widget getWidget() {
		return widget.get();
	}
	
	public GraphicDriver getDriver() {
		return driver;
	}
	
}
