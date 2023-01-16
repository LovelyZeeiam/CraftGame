package xueli.game2.ui;

import xueli.game2.renderer.ui.Gui;

@Deprecated
public class UIWidget {
	
	private final UIContext ctx;
	@SuppressWarnings("unused")
	private final Gui gui;
	
	public UIWidget(UIContext context) {
		this.ctx = context;
		this.gui = ctx.getGui();
		
	}
	
	public void tick() {
		
	}
	
}
