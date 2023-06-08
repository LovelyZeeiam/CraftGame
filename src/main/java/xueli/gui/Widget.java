package xueli.gui;

import java.beans.PropertyChangeEvent;
import java.lang.ref.WeakReference;

import org.lwjgl.utils.vector.Vector2d;
import org.lwjgl.utils.vector.Vector2f;

import xueli.gui.skin.DefaultSkin;

/**
 * <p>The controller of widget, containing position, size and other components. It stores and manages its model and view, receives all outside invokes and events and publish all necessary interface.</p> 
 * 
 * <p>Designed for MVC mode: https://baijiahao.baidu.com/s?id=1705333225423245283</p>
 * 
 * @author Xueli
 */
public class Widget extends WidgetBean {
	
//	private static final Logger LOGGER = new Logger();

	public static final String PROPERTY_POSITION = "pos";
	public static final String PROPERTY_SIZE = "size";

	protected Widget parent = null;

	private float x = 0.0f, y = 0.0f; // Relative to the parent
	private float width = 0.0f, height = 0.0f;
	
	private final WidgetUI ui;
	
	public Widget(UIContext ctx) {
		super(ctx);
		this.registerPropertyChange();
		this.ui = new WidgetUI(new WeakReference<>(this), ctx);
		
		setSkin(DefaultSkin.SHARED_INSTANCE.getSkin(getClass()));
		
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
		this.setPosition(x, y);
		this.setDimension(width, height);

	}
	
	public void setPosition(float x, float y) {
		boolean moved = this.x != x || this.y != y;
		Vector2f oldPos = new Vector2f(this.x, this.y);
		this.x = x;
		this.y = y;
		
		if (moved) {
			this.firePropertyChange(PROPERTY_POSITION, oldPos, new Vector2d(x, y));

		}
		
	}
	
	public void setDimension(float width, float height) {
		boolean resized = this.width != width || this.height != height;
		Vector2f oldSize = new Vector2f(this.width, this.height);
		this.width = width;
		this.height = height;
		
		if (resized) {
			this.firePropertyChange(PROPERTY_SIZE, oldSize, new Vector2d(width, height));
		}
		
	}
	
	// "Repaint" should happen when an area of widget should be updated
	// So if only the position of the widget is changed, the parent should be
	// updated
	protected void announceRepaint(float x, float y, float width, float height) {
		this.ui.announceRepaint(x, y, width, height);

	}
	
	protected void announceRepaint() {
		this.ui.announceRepaint();
	}
	
	// Should only be called from Skin or test!
//	public SizeHint measure() {
//		return this.ui.measure();
//	}
	
	// Should only be called from Skin or test!
//	public void doPaint() {
//		this.ui.doPaint();
//	}

	protected void dispatchEvent(UIEvent event) {
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
	
	public void setSkin(WidgetSkin skin) {
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
