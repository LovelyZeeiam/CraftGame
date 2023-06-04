package xueli.gui;

import xueli.game2.display.GameDisplay;
import xueli.game2.math.TriFuncMap;
import xueli.game2.renderer.ui.NanoGui;
import xueli.gui.widget.Rectangle;

public class UITest extends GameDisplay {

	private NanoGui uiDriver;
	private UIContext context;

	public UITest() {
		super(800, 600, "UITest");
	}

	Rectangle testWidget;

	@Override
	protected void renderInit() {
		this.uiDriver = new NanoGui();
		this.uiDriver.reload();

		this.context = new UIContext(uiDriver, this);
		var rootWidget = context.getRootWidget();
//		rootWidget.setUseImmediateMode(true);

		testWidget = new Rectangle(context);
		testWidget.setBounds(50, 50, 100, 100);
		rootWidget.add(testWidget);

		this.display.setMouseGrabbed(false);

	}

	@Override
	protected void render() {
//		GL11.glClearColor(1, 1, 1, 1);

		long time = System.currentTimeMillis();
		testWidget.setBounds(10, (float) (200 + 100 * TriFuncMap.sin((time % 1500) * 360.0 / 1500.0)),
				(float) (500 + 100 * TriFuncMap.sin((time % 2000) * 360.0 / 2000.0)),
				(float) (300 + 100 * TriFuncMap.sin((time % 2500) * 360.0 / 2500.0)));

//		System.out.println("=== render start ===");
		this.context.tick();
//		System.out.println("*** render end ***");

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
