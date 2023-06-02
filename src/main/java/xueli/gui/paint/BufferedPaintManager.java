package xueli.gui.paint;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Objects;

import xueli.gui.widget.Widget;
import xueli.gui.widget.WidgetSkin;

// This paint manager only draw the widget itself, but widget's "x" and "y" is relative to its parent
// It uses frame buffer, but the painting can lag if size change is too frequent
public class BufferedPaintManager extends PaintManager {

//	private static final Logger LOGGER = new Logger();
	
	private FrameBuffer frameBuffer;

	private LinkedList<Announcement> announcements = new LinkedList<>(); // Not thread safe

	public BufferedPaintManager(WeakReference<Widget> widget, GraphicDriver driver) {
		super(widget, driver);
		
		// Can't put in parent class initialization because "announcements" can be null there!
		this.announceSizeChange();

		Widget w = widget.get();
		if (w == null)
			throw new NullPointerException();
		this.announceRepaint(0, 0, w.getWidth(), w.getHeight());
		
	}

	@Override
	public void announceSizeChange() {
		this.tryMergeAnnouncement(new SizeChangeAnnouncement());
	}

	@Override
	public void announceRepaint(float x, float y, float width, float height) {
		this.tryMergeAnnouncement(new RepaintAnnouncement(x, y, width, height));
	}
	
	private void tryMergeAnnouncement(Announcement next) {
		Announcement last = announcements.peekLast();
		if(last == null) {
			announcements.add(next);
			return;
		}
		
		if(last instanceof SizeChangeAnnouncement && next instanceof SizeChangeAnnouncement)
			return;
		if(last instanceof RepaintAnnouncement && next instanceof RepaintAnnouncement) {
			if(last.equals(next))
				return;
		}
		
		announcements.add(next);
		
	}

	@Override
	public void doPaint() {
		Widget w = getWidget();
		if (w == null)
			return;
		
		Runnable announcement;
		while (!this.announcements.isEmpty()) {
			announcement = this.announcements.pop();
//			LOGGER.info("[UIPaint] " + announcement.toString() + " at " + w.toString());
			
			announcement.run();
			
		}

		int imageId = frameBuffer.getImageId();
		getDriver().drawImage(w.getX(), w.getY(), w.getWidth(), w.getHeight(), 1.0f, imageId);
		
	}

	@Override
	public void release() {
		if(frameBuffer != null) {
			frameBuffer.release();
			frameBuffer = null;
		}
		
	}
	
	private interface Announcement extends Runnable {
	}

	private class SizeChangeAnnouncement implements Announcement {
		@Override
		public void run() {
			Widget w = getWidget();
			if (w == null)
				return;

			// Make a bigger box to include the widget. To support float size and position
			// of widget!
			int realWidth = (int) Math.ceil(w.getWidth());
			int realHeight = (int) Math.ceil(w.getHeight());
			if (frameBuffer == null)
				frameBuffer = getDriver().createFrameBuffer(realWidth, realHeight);
			else
				frameBuffer.resize(realWidth, realHeight);

		}

		@Override
		public String toString() {
			return "SizeChangeAnnouncement []";
		}
		
	}

	private class RepaintAnnouncement implements Announcement {

		float x, y, width, height;

		public RepaintAnnouncement(float x, float y, float width, float height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		@Override
		public void run() {
			Widget w = getWidget();
			if (w == null)
				return;

			WidgetSkin skin = w.getSkin();
			GraphicDriver driver = getDriver();
			
			driver.pushFrameBuffer(frameBuffer);
			// this function clears the screen? When repainting we just want to repaint that exact area!
			driver.begin(frameBuffer.getWidth(), frameBuffer.getHeight());
			skin.paint(w, x, y, width, height, driver);
			driver.finish();
			driver.popFrameBuffer();

		}

		@Override
		public String toString() {
			return "RepaintAnnouncement [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(height, width, x, y);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			RepaintAnnouncement other = (RepaintAnnouncement) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Float.floatToIntBits(height) == Float.floatToIntBits(other.height)
					&& Float.floatToIntBits(width) == Float.floatToIntBits(other.width)
					&& Float.floatToIntBits(x) == Float.floatToIntBits(other.x)
					&& Float.floatToIntBits(y) == Float.floatToIntBits(other.y);
		}

		private BufferedPaintManager getEnclosingInstance() {
			return BufferedPaintManager.this;
		}
		
	}

}
