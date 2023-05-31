package xueli.gui;

import xueli.game2.display.GameDisplay;
import xueli.game2.renderer.ui.Gui;

public class UITest extends GameDisplay {
	
	private Gui uiDriver;
	private UIContext context;
	
	public UITest() {
		super(800, 600, "UITest");
	}

	@Override
	protected void renderInit() {
		this.uiDriver = new Gui();
		this.uiDriver.reload();
		
		this.context = new UIContext(uiDriver, this);
		
		this.display.setMouseGrabbed(false);
		
	}

	@Override
	protected void render() {
		this.context.tick();
		
	}

	@Override
	protected void renderRelease() {
		this.context.release();
		this.uiDriver.release();
		
	}
	
	public static void main(String[] args) {
		new UITest().run();
		
	}

}
