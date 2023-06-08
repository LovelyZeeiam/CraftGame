package xueli.gui;

import java.lang.ref.WeakReference;

import xueli.gui.driver.GraphicDriver;

public abstract class PaintManager {
	
	private final WidgetAccess widget;

	private final GraphicDriver driver;

	public PaintManager(WidgetAccess widget, GraphicDriver driver) {
		this.widget = widget;
		this.driver = driver;
		
	}

	public abstract void announceSizeChange();
	
	public abstract void announceRepaint(float x, float y, float width, float height);
	
	protected abstract void doPaint();

	protected abstract void release();
	
	public SizeHint measure() {
		return this.widget.measure();
	}

	protected void widgetRealPaint(float x, float y, float width, float height) {
		this.widget.doPaint(x, y, width, height);
	}

	protected float getWidgetWidth() {
		return this.widget.getWidth();
	}

	protected float getWidgetHeight() {
		return this.widget.getHeight();
	}

	protected float getWidgetX() {
		return this.widget.getX();
	}

	protected float getWidgetY() {
		return this.widget.getY();
	}

	public GraphicDriver getDriver() {
		return driver;
	}

	public static interface WidgetAccess {

		public SizeHint measure();
		public void doPaint(float x, float y, float width, float height);
		public float getX();
		public float getY();
		public float getWidth();
		public float getHeight();

	}

}
