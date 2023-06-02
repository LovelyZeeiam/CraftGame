package xueli.gui;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import xueli.game2.display.event.WindowSizedEvent;
import xueli.gui.driver.GraphicDriver;
import xueli.utils.MyWeakHashMap;

public class PaintMaster {

	private final UIContext ctx;
	private final GraphicDriver driver;
//	private final FrameBuffer rootFrameBuffer;

	private final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
	private final MyWeakHashMap<Widget, PaintManager> painters = new MyWeakHashMap<>(referenceQueue, PaintManager::release);

	public PaintMaster(UIContext ctx) {
		this.ctx = ctx;
		this.driver = ctx.getDriver();
//		this.rootFrameBuffer = driver.createFrameBuffer(ctx.getDisplayWidth(), ctx.getDisplayHeight());
		
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
				new ImmediatePaintManager(new WeakReference<Widget>(widget), driver)
				: new BufferedPaintManager(new WeakReference<Widget>(widget), driver);
		newManager.announceRepaint(0, 0, widget.getWidth(), widget.getHeight());
		this.painters.put(widget, newManager);
		return newManager;
	}
	
	private void drawRoot() {
//		this.driver.pushFrameBuffer(rootFrameBuffer);
//		this.driver.popFrameBuffer();
		
		this.driver.begin(ctx.getDisplayWidth(), ctx.getDisplayHeight());
		ctx.getRootWidget().doPaint();
//		this.driver.setColor(Color.blue);
//		this.driver.drawFilledRect(50, 50, 600, 500, FillType.COLOR);
		this.driver.finish();
		
	}
	
	public void tick() {
		this.drawRoot();
		
	}
	
	public void onScreenSize(WindowSizedEvent e) {
//		this.rootFrameBuffer.resize(e.width(), e.height());
	}

}
