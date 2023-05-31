package xueli.gui.paint;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

import xueli.gui.widget.Widget;
import xueli.gui.widget.WidgetSkin;

// This paint manager only draw the widget itself, but widget's "x" and "y" is relative to its parent
public class PaintManager {

	private final WeakReference<Widget> widget;

	private final GraphicDriver driver;
//	private OffsetGraphicDriver fixedPaintDriver;

	private FrameBuffer frameBuffer;
	private float fixedOffsetX, fixedOffsetY;

	private LinkedList<Runnable> announcements = new LinkedList<>();

	public PaintManager(WeakReference<Widget> widget, GraphicDriver driver) {
		this.widget = widget;
		this.driver = driver;

		this.announceSizeChange();

		Widget w = widget.get();
		if (w == null)
			throw new NullPointerException();
		this.announceRepaint(0, 0, w.getWidth(), w.getHeight());

	}

	public void announceSizeChange() {
		this.announcements.add(new SizeChangeAnnouncement());
	}

	public void announceRepaint(float x, float y, float width, float height) {
		this.announcements.add(new RepaintAnnouncement(x, y, width, height));
	}

	public void doPaint() {
		Widget w = widget.get();
		if (w == null)
			return;

		Runnable announcement;
		while (!this.announcements.isEmpty()) {
			announcement = this.announcements.pop();
			announcement.run();
		}

		int imageId = frameBuffer.getImageId();
		driver.drawImage(w.getX(), w.getY(), w.getWidth(), w.getHeight(), 1.0f, imageId);

	}

	void release() {
		frameBuffer.release();
		frameBuffer = null;
	}

	// TODO: merge the unnecessary repaint request
	private interface Announcement extends Runnable {
		public Announcement tryMergeWithNext(Announcement next);
	}

	private class SizeChangeAnnouncement implements Runnable {
		@Override
		public void run() {
			Widget w = widget.get();
			if (w == null)
				return;

			// Make a bigger box to include the widget. To support float size and position
			// of widget!
			int realWidth = (int) Math.ceil(w.getWidth());
			int realHeight = (int) Math.ceil(w.getHeight());
			if (frameBuffer == null)
				frameBuffer = driver.createFrameBuffer(realWidth, realHeight);
			else
				frameBuffer.resize(realWidth, realHeight);
			fixedOffsetX = (float) realWidth - w.getWidth();
			fixedOffsetY = (float) realHeight - w.getHeight();

//			System.out.println(this);

		}
	}

	private class RepaintAnnouncement implements Runnable {

		private float x, y, width, height;

		public RepaintAnnouncement(float x, float y, float width, float height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		@Override
		public void run() {
			Widget w = widget.get();
			if (w == null)
				return;

			WidgetSkin skin = w.getSkin();
			driver.pushFrameBuffer(frameBuffer);
			// this function clears the screen? When repainting we just wwant to repaint that exact area!
			driver.begin(frameBuffer.getWidth(), frameBuffer.getHeight());
			skin.paint(w, x + fixedOffsetX, y + fixedOffsetY, width, height,
					new OffsetGraphicDriver(fixedOffsetX, fixedOffsetY, driver));
			driver.finish();
			driver.popFrameBuffer();

//			System.out.println(this);

		}
	}

}
