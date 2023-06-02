package xueli.gui;

import java.lang.ref.WeakReference;

import xueli.gui.driver.GraphicDriver;
import xueli.gui.driver.OffsetGraphicDriver;

// Every frame it just draws all
public class ImmediatePaintManager extends PaintManager {
	
	private final OffsetGraphicDriver offsetGraphicDriver;
	
	public ImmediatePaintManager(WeakReference<Widget> widget, GraphicDriver driver) {
		super(widget, driver);
		this.offsetGraphicDriver = new OffsetGraphicDriver(0, 0, driver);
		
		// Can't put in parent class initialization because "announcements" can be null there!
		this.announceSizeChange();

		Widget w = widget.get();
		if (w == null)
			throw new NullPointerException();
		this.announceRepaint(0, 0, w.getWidth(), w.getHeight());
		
	}

	@Override
	public void announceSizeChange() {
	}

	@Override
	public void announceRepaint(float x, float y, float width, float height) {
	}

	@Override
	public void doPaint() {
		Widget w = getWidget();
		if (w == null)
			return;
		
		WidgetSkin skin = w.getSkin();
		this.offsetGraphicDriver.setOffset(w.getX(), w.getY());
		
		float width = w.getWidth();
		float height = w.getHeight();
		if(width == 0 || height == 0) return;
		skin.paint(w, 0, 0, width, height, offsetGraphicDriver);
		
	}

	@Override
	public void release() {
	}

}
