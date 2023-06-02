package xueli.gui;

import java.beans.PropertyChangeEvent;
import java.lang.ref.WeakReference;

import org.lwjgl.utils.vector.Vector2d;
import org.lwjgl.utils.vector.Vector2f;

public class Widget extends WidgetBean {

	public static final String PROPERTY_POSITION = "pos";
	public static final String PROPERTY_SIZE = "size";

	private Widget parent = null;

	private float x = 0.0f, y = 0.0f;
	private float width = 0.0f, height = 0.0f;
	
	private final WidgetUI ui;
	
	public Widget(GameUIContext ctx) {
		super(ctx);
		this.registerPropertyChange();
		this.ui = new WidgetUI(new WeakReference<Widget>(this), ctx);
		
	}

	private void registerPropertyChange() {
		this.registerPropertyChangeListener(PROPERTY_SIZE, this::onPropertySizeChange);
		this.registerPropertyChangeListener(PROPERTY_POSITION, this::onPropertyPositionChange);
		
	}
	
	private void onPropertySizeChange(PropertyChangeEvent event) {
		var painter = ctx.getPaintMaster().getPaintManager(this);
		painter.announceSizeChange();
		this.announceRepaint();
	}

	private void onPropertyPositionChange(PropertyChangeEvent event) {
		if (this.parent == null)
			return; // it is the root node indeed!
		this.parent.announceRepaint();
	}

	// Set the bounds. If this is a view group, then the layout code can be added to
	// property change listener.
	public void setBounds(float x, float y, float width, float height) {
		boolean moved = this.x != x || this.y != y;
		Vector2f oldPos = new Vector2f(this.x, this.y);
		this.x = x;
		this.y = y;

		boolean resized = this.width != width || this.height != height;
		Vector2f oldSize = new Vector2f(this.width, this.height);
		this.width = width;
		this.height = height;

		if (moved) {
			this.firePropertyChange(PROPERTY_POSITION, oldPos, new Vector2d(x, y));

		}
		if (resized) {
			this.firePropertyChange(PROPERTY_SIZE, oldSize, new Vector2d(width, height));

		}

	}

	// "Repaint" should happen when an area of widget should be updated
	// So if only the position of the widget is changed, the parent should be
	// updated
	public void announceRepaint(float x, float y, float width, float height) {
		this.ui.announceRepaint(x, y, width, height);

	}
	
	public void announceRepaint() {
		this.ui.announceRepaint();
	}

	public void dispatchEvent(UIEvent event) {
//		switch(event.type()) {
//		case UIEvent.EVENT_KEY -> {
//			
//		}
//		case UIEvent.EVENT_WINDOW_SIZED -> {
//			
//		}
//		case UIEvent.EVENT_MOUSE_INPUT -> {
//			
//		}
//		case UIEvent.EVENT_CURSOR_POSITION -> {
//			
//		}
//		default -> {
//			LOGGER.warning("Unknown event: " + event.toString());
//		}
//		}
	}
	
	public final void setSkin(WidgetSkin skin) {
		this.ui.setSkin(skin);
	}

	public final float getX() {
		return x;
	}

	public final float getY() {
		return y;
	}

	public final float getWidth() {
		return width;
	}

	public final float getHeight() {
		return height;
	}

	public WidgetSkin getSkin() {
		return this.ui.getSkin();
	}
	
	public void setUseImmediateMode(boolean useImmediateMode) {
		this.ui.setUseImmediateMode(useImmediateMode);
	}
	
	public boolean isUseImmediateMode() {
		return this.ui.getUseImmediateMode();
	}

}
