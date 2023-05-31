package xueli.gui;

import java.util.LinkedList;

import xueli.game2.display.GameDisplay;
import xueli.game2.display.event.CursorPositionEvent;
import xueli.game2.display.event.WindowKeyEvent;
import xueli.game2.display.event.WindowMouseButtonEvent;
import xueli.game2.display.event.WindowSizedEvent;
import xueli.game2.math.TriFuncMap;
import xueli.gui.paint.FrameBuffer;
import xueli.gui.paint.GraphicDriver;
import xueli.gui.paint.PaintMaster;
import xueli.gui.widget.TestWidget;

public class UIContext {

//	private static final Logger LOGGER = new Logger();

	private final GameDisplay display;
	private final GraphicDriver driver;
	private final PaintMaster paintManager;
	private final LinkedList<UIEvent> eventQueue = new LinkedList<UIEvent>();

	private final TestWidget root;
	private final FrameBuffer rootFrameBuffer;

	public UIContext(GraphicDriver driver, GameDisplay display) {
		this.display = display;
		this.driver = driver;
		this.paintManager = new PaintMaster(driver);
		this.registerEventListener();

		this.root = new TestWidget(this);
		this.rootFrameBuffer = driver.createFrameBuffer(display.getWidth(), display.getHeight());

	}

	public void postEvent(UIEvent event) {
		eventQueue.add(event);
	}
	
	
	private void drawRoot() {
		long time = System.currentTimeMillis();
		root.setBounds(10, (float)(200 + 100 * TriFuncMap.sin((time % 1500) * 360.0 / 1500.0)), 100, 100);
		
		this.driver.pushFrameBuffer(rootFrameBuffer);
		this.paintManager.getPaintManager(root).doPaint();
		this.driver.popFrameBuffer();
		
		this.driver.begin(display.getWidth(), display.getHeight());
//		this.driver.setColor(Color.blue);
//		this.driver.drawFilledRect(50, 50, 600, 500, FillType.COLOR);
		this.driver.drawImage(0, 0, display.getWidth(), display.getHeight(), 1.0f, this.rootFrameBuffer.getImageId());
		this.driver.finish();
		
	}

	public void tick() {
		this.processEvent();
		this.drawRoot();
		
	}

	public void release() {
		this.unregisterEventListener();

	}

	private void registerEventListener() {
		display.eventbus.register(WindowKeyEvent.class, this::onEventbusKeyEvent);
		display.eventbus.register(WindowSizedEvent.class, this::onEventbusSizedEvent);
		display.eventbus.register(WindowMouseButtonEvent.class, this::onEventbusMouseButtonEvent);
		display.eventbus.register(CursorPositionEvent.class, this::onEventbusCursorPositionEvent);

	}

	private void unregisterEventListener() {
		display.eventbus.unregister(WindowKeyEvent.class, this::onEventbusKeyEvent);
		display.eventbus.unregister(WindowSizedEvent.class, this::onEventbusSizedEvent);
		display.eventbus.unregister(WindowMouseButtonEvent.class, this::onEventbusMouseButtonEvent);
		display.eventbus.unregister(CursorPositionEvent.class, this::onEventbusCursorPositionEvent);

	}

	private void onEventbusKeyEvent(WindowKeyEvent e) {
		this.postEvent(new UIEvent(UIEvent.EVENT_KEY, e));
	}

	private void onEventbusSizedEvent(WindowSizedEvent e) {
		this.postEvent(new UIEvent(UIEvent.EVENT_WINDOW_SIZED, e));
		this.rootFrameBuffer.resize(e.width(), e.height());
	}

	private void onEventbusMouseButtonEvent(WindowMouseButtonEvent e) {
		this.postEvent(new UIEvent(UIEvent.EVENT_MOUSE_INPUT, e));
	}

	private void onEventbusCursorPositionEvent(CursorPositionEvent e) {
		this.postEvent(new UIEvent(UIEvent.EVENT_CURSOR_POSITION, e));
	}

	private void processEvent() {
		UIEvent e;
		while ((e = this.eventQueue.poll()) != null) {
			root.dispatchEvent(e);
		}
	}

	public PaintMaster getPaintMaster() {
		return paintManager;
	}

}
