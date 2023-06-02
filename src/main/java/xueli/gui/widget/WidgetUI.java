package xueli.gui.widget;

import java.beans.PropertyChangeEvent;
import java.lang.ref.WeakReference;

import xueli.gui.GameUIContext;

class WidgetUI {
	
	public static final String PROPERTY_SKIN = "skin";
	
	// Using Weak Reference here to help GC
	private final WeakReference<Widget> controller;
	private final GameUIContext context;
	
	private WidgetSkin skin;
	private boolean useImmediateMode = false;
	
	public WidgetUI(WeakReference<Widget> controller, GameUIContext context) {
		this.controller = controller;
		this.context = context;
		this.registerChangeListener();
		
	}
	
	private void registerChangeListener() {
		getController().registerPropertyChangeListener(PROPERTY_SKIN, this::onPropertySkinChange);
		
	}
	
	public void setUseImmediateMode(boolean useImmediateMode) {
		this.useImmediateMode = useImmediateMode;
	}
	
	public void setSkin(WidgetSkin skin) {
		if(skin == this.skin) return;
		
		Widget controller = getController();
		
		WidgetSkin oldSkin = this.skin;
		if (oldSkin != null) {
			oldSkin.uninstall(controller);
		}
		this.skin = skin;
		if (skin != null) {
			skin.install(controller);
		}
		controller.firePropertyChange(PROPERTY_SKIN, oldSkin, skin);
		
	}
	
	private void onPropertySkinChange(PropertyChangeEvent event) {
		this.announceRepaint();
	}
	
	public void announceRepaint() {
		this.announceRepaint(0, 0, getController().getWidth(), getController().getHeight());
	}
	
	public void announceRepaint(float x, float y, float width, float height) {
		// Maybe should be processed inside every widget
		// if(x > this.width || y > this.height) return;
		var painter = this.context.getPaintMaster().getPaintManager(getController(), useImmediateMode);
		painter.announceRepaint(x, y, width, height);
		
	}
	
	// Shouldn't be null because UI and Widget have the same lifetime
	private Widget getController() {
		return controller.get();
	}
	
	public boolean getUseImmediateMode() {
		return useImmediateMode;
	}
	
	public WidgetSkin getSkin() {
		return skin;
	}

}
