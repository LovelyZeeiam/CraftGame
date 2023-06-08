package xueli.gui;

import xueli.gui.driver.GraphicDriver;
import xueli.utils.MyWeakHashMap;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public class PaintMaster {

	private final GraphicDriver driver;

	private final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
	private final MyWeakHashMap<Widget, PaintManager> painters = new MyWeakHashMap<>(referenceQueue, PaintManager::release);

	private final WeakHashMap<Widget, Boolean> immediateModeMap = new WeakHashMap<>();

	public PaintMaster(GraphicDriver driver) {
		this.driver = driver;

	}
	
	public PaintManager getPaintManager(Widget widget) {
		return this.getPaintManager(widget, false);
	}

	public PaintManager getPaintManager(Widget widget, boolean immediate) {
		PaintManager manager = this.painters.get(widget);
		if(manager == null) {
			return this.createAndStorePaintManager(widget, immediate);
		}
		
		if((immediate && manager instanceof BufferedPaintManager)
				|| (!immediate && manager instanceof ImmediatePaintManager)) {
			manager.release();
		} else {
			return manager;
		}
		return this.createAndStorePaintManager(widget, immediate);
	}
	
	private PaintManager createAndStorePaintManager(Widget widget, boolean immediate) {
		PaintManager newManager = immediate ?
				new ImmediatePaintManager(new MyWidgetAccess(new WeakReference<>(widget)), driver)
				: new BufferedPaintManager(new MyWidgetAccess(new WeakReference<>(widget)), driver);
		newManager.announceRepaint(0, 0, widget.getWidth(), widget.getHeight());
		this.painters.put(widget, newManager);
		return newManager;
	}

	public void drawWidget(Widget widget) {
		this.getPaintManager(widget, this.isUseImmediateMode(widget)).doPaint();
	}

	public void drawWidget(Widget widget, GraphicDriver newDriver) {
		this.getPaintManager(widget, this.isUseImmediateMode(widget)).doPaint();
	}

	public void setUseImmediateMode(Widget widget, boolean use) {
		immediateModeMap.put(widget, use);
	}

	public boolean isUseImmediateMode(Widget widget) {
		Boolean objVal = immediateModeMap.get(widget);
		return objVal == null ? false : objVal.booleanValue();
	}

	public void tick() {
	}

	public GraphicDriver getDriver() {
		return driver;
	}

	private class MyWidgetAccess implements PaintManager.WidgetAccess {

		private final WeakReference<Widget> widget;

		public MyWidgetAccess(WeakReference<Widget> widget) {
			this.widget = widget;
		}

		@Override
		public SizeHint measure() {
			var widget = this.widget.get();
			return widget.getSkin().measure(widget, driver);
		}

		@Override
		public void doPaint(float x, float y, float width, float height) {
			var widget = this.widget.get();
			widget.getSkin().paint(widget, x, y, width, height, PaintMaster.this);
		}

		@Override
		public float getX() {
			return this.widget.get().getX();
		}

		@Override
		public float getY() {
			return this.widget.get().getY();
		}

		@Override
		public float getWidth() {
			return this.widget.get().getWidth();
		}

		@Override
		public float getHeight() {
			return this.widget.get().getHeight();
		}
	}

}
