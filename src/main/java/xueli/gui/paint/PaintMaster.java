package xueli.gui.paint;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import xueli.gui.widget.Widget;
import xueli.utils.MyWeakHashMap;

public class PaintMaster {

//	private final UIContext ctx;
	private final GraphicDriver driver;

	private final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
	private final MyWeakHashMap<Widget, PaintManager> painters = new MyWeakHashMap<>(referenceQueue, PaintManager::release);

	public PaintMaster(GraphicDriver driver) {
//		this.ctx = ctx;
		this.driver = driver;

	}

	public PaintManager getPaintManager(Widget widget) {
		return this.painters.computeIfAbsent(widget, w -> new PaintManager(new WeakReference<Widget>(w), driver));
	}

	public void tick() {
	}

}
