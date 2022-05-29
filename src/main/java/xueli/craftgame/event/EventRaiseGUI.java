package xueli.craftgame.event;

import xueli.craftgame.renderer.view.IngameView;

public class EventRaiseGUI {

	private final Class<? extends IngameView> viewClazz;

	public EventRaiseGUI(Class<? extends IngameView> viewClazz) {
		this.viewClazz = viewClazz;
	}

	public Class<? extends IngameView> getViewClass() {
		return viewClazz;
	}

}
