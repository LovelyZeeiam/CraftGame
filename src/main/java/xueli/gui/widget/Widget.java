package xueli.gui.widget;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import javax.swing.text.SimpleAttributeSet;

import org.lwjgl.utils.vector.Vector2d;
import org.lwjgl.utils.vector.Vector2f;

import xueli.gui.UIContext;
import xueli.gui.UIEvent;

public class Widget {

	public static final String PROPERTY_POSITION = "pos";
	public static final String PROPERTY_SIZE = "size";
	public static final String PROPERTY_SKIN = "skin";

	private final UIContext ctx;

	private Widget parent = null;

	private float x = 0.0f, y = 0.0f;
	private float width = 0.0f, height = 0.0f;

	private WidgetSkin skin;
	private boolean useImmediateMode = false;

	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private final SimpleAttributeSet attributes = new SimpleAttributeSet();

	public Widget(UIContext ctx) {
		this.ctx = ctx;
		this.registerPropertyChange();
	}

	private void registerPropertyChange() {
		this.changeSupport.addPropertyChangeListener(PROPERTY_SIZE, this::onPropertySizeChange);
		this.changeSupport.addPropertyChangeListener(PROPERTY_POSITION, this::onPropertyPositionChange);
		this.changeSupport.addPropertyChangeListener(PROPERTY_SKIN, this::onPropertySkinChange);

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
			changeSupport.firePropertyChange(PROPERTY_POSITION, oldPos, new Vector2d(x, y));

		}
		if (resized) {
			changeSupport.firePropertyChange(PROPERTY_SIZE, oldSize, new Vector2d(width, height));

		}

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

	private void onPropertySkinChange(PropertyChangeEvent event) {
		this.announceRepaint();
	}

	// "Repaint" should happen when an area of widget should be updated
	// So if only the position of the widget is changed, the parent should be
	// updated
	public void announceRepaint(float x, float y, float width, float height) {
		// Maybe should be processed inside every widget
		// if(x > this.width || y > this.height) return;

		var painter = ctx.getPaintMaster().getPaintManager(this, useImmediateMode);
		painter.announceRepaint(x, y, width, height);

	}

	public void announceRepaint() {
		this.announceRepaint(0, 0, width, height);
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

	public final Object getAttribute(Object name) {
		return this.attributes.getAttribute(name);
	}

	public final void removeAttribute(Object name) {
		this.attributes.removeAttribute(name);
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

	public final void setSkin(WidgetSkin skin) {
		WidgetSkin oldSkin = this.skin;
		if (oldSkin != null) {
			oldSkin.uninstall(this);
		}
		this.skin = skin;
		if (skin != null) {
			skin.install(this);
		}
		this.changeSupport.firePropertyChange(PROPERTY_SKIN, oldSkin, skin);
	}

	public final WidgetSkin getSkin() {
		return skin;
	}
	
	public void setUseImmediateMode(boolean useImmediateMode) {
		this.useImmediateMode = useImmediateMode;
	}
	
	public boolean isUseImmediateMode() {
		return useImmediateMode;
	}

}
