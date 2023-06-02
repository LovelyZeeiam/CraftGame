package xueli.gui;

import java.util.LinkedList;

import xueli.game2.display.GameDisplay;
import xueli.game2.display.event.CursorPositionEvent;
import xueli.game2.display.event.WindowKeyEvent;
import xueli.game2.display.event.WindowMouseButtonEvent;
import xueli.game2.display.event.WindowSizedEvent;
import xueli.game2.math.TriFuncMap;
import xueli.gui.driver.GraphicDriver;
import xueli.gui.driver.PaintMaster;
import xueli.gui.widget.TestWidget;

public class GameUIContext {

//	private static final Logger LOGGER = new Logger();

	// TODO: re-design Graphic Driver or create a new interface to adapt to different display
	// Maybe next time Windows API is used directly (never
	private final GameDisplay display;
	private final GraphicDriver driver;
	
	private final PaintMaster paintManager;
	private final LinkedList<UIEvent> eventQueue = new LinkedList<UIEvent>(); // Not thread safe

	private final TestWidget root;

	public GameUIContext(GraphicDriver driver, GameDisplay display) {
		this.display = display;
		this.driver = driver;
		this.paintManager = new PaintMaster(this);
		this.registerEventListener();

		this.root = new TestWidget(this);
		
	}
	
	public void postEvent(UIEvent event) {
		this.tryMergeWithLastEvent(event);
	}
	
	private void tryMergeWithLastEvent(UIEvent next) {
		UIEvent last = eventQueue.peek();
		if(last == null) {
			eventQueue.add(next);
			return;
		}
		
		if((last.type() == UIEvent.EVENT_WINDOW_SIZED && next.type() == UIEvent.EVENT_WINDOW_SIZED)
				|| (last.type() == UIEvent.EVENT_CURSOR_POSITION && next.type() == UIEvent.EVENT_CURSOR_POSITION)) {
			eventQueue.poll();
			eventQueue.add(next);
		} else {
			eventQueue.add(next);
		}
	}

	public void tick() {
		long time = System.currentTimeMillis();
		root.setBounds(10,
				(float)(200 + 100 * TriFuncMap.sin((time % 1500) * 360.0 / 1500.0)),
				(float)(500 + 100 * TriFuncMap.sin((time % 2000) * 360.0 / 2000.0)),
				(float)(300 + 100 * TriFuncMap.sin((time % 2500) * 360.0 / 2500.0)));
		
		this.processEvent();
		this.paintManager.tick();
		
	}

	public void release() {
		this.unregisterEventListener();

	}

	private void registerEventListener() {
		// TODO: no more register here
		// maybe the driver is responsible for this
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
		this.paintManager.onScreenSize(e);
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
//			LOGGER.info("[UIEvent] " + e.toString());
			root.dispatchEvent(e);
		}
	}

	public PaintMaster getPaintMaster() {
		return paintManager;
	}
	
	public TestWidget getRootWidget() {
		return root;
	}
	
	public int getDisplayWidth() {
		return this.display.getWidth();
	}
	
	public int getDisplayHeight() {
		return this.display.getHeight();
	}
	
	public GraphicDriver getDriver() {
		return driver;
	}

}
