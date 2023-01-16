package xueli.game2.ui;

import xueli.game2.renderer.ui.Gui;
import xueli.game2.renderer.ui.Overlay;
import xueli.utils.events.EventBus;

@Deprecated
public class UIContext implements Overlay {
	
	protected final EventBus uiEventBus = new EventBus();
	
	private Gui gui;
	private UIWidget rootWidget;
	
	public UIContext() {
	}
	
	@Override
	public void init(Gui gui) {
		this.gui = gui;
	}

	@Override
	public void reload() {
		
	}

	@Override
	public void render() {
		
	}

	@Override
	public void release() {
		
	}
	
	public void postUIEvent(Object obj) {
		
	}
	
	public Gui getGui() {
		return gui;
	}
	
	public UIWidget getRootWidget() {
		return rootWidget;
	}
	
	public void setRootWidget(UIWidget rootWidget) {
		this.rootWidget = rootWidget;
	}

}
