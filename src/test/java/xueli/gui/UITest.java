package xueli.gui;

import xueli.game2.display.GameDisplay;
import xueli.game2.renderer.ui.NanoGui;

public class UITest extends GameDisplay {
	
	private NanoGui uiDriver;
	private GameUIContext context;
	
	public UITest() {
		super(800, 600, "UITest");
	}

	@Override
	protected void renderInit() {
		this.uiDriver = new NanoGui();
		this.uiDriver.reload();
		
		this.context = new GameUIContext(uiDriver, this);
		
		this.display.setMouseGrabbed(false);
		
	}

	@Override
	protected void render() {
//		GL11.glClearColor(1, 1, 1, 1);
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
