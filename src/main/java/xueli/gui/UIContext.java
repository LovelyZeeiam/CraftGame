package xueli.gui;

import java.util.LinkedList;

import xueli.game2.display.GameDisplay;
import xueli.game2.display.event.CursorPositionEvent;
import xueli.game2.display.event.WindowKeyEvent;
import xueli.game2.display.event.WindowMouseButtonEvent;
import xueli.game2.display.event.WindowSizedEvent;
import xueli.utils.logger.Logger;

public class UIContext {
	
	private static final Logger LOGGER = new Logger();
	
	private final GameDisplay display;
	private final GraphicDriver driver;
	private final LinkedList<UIEvent> eventQueue = new LinkedList<UIEvent>(); 
	
	public UIContext(GraphicDriver driver, GameDisplay display) {
		this.display = display;
		this.driver = driver;
		
		this.registerEventListener();
		
	}
	
	public void postEvent(UIEvent event) {
		eventQueue.add(event);
	}
	
	public void tick() {
		this.processEvent();
		
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
	}
	
	private void onEventbusMouseButtonEvent(WindowMouseButtonEvent e) {
		this.postEvent(new UIEvent(UIEvent.EVENT_MOUSE_INPUT, e));
	}
	
	private void onEventbusCursorPositionEvent(CursorPositionEvent e) {
		this.postEvent(new UIEvent(UIEvent.EVENT_CURSOR_POSITION, e));
	}
	
	private void processEvent() {
		UIEvent e;
		while((e = this.eventQueue.poll()) != null) {
			this.dispatchEvent(e);
		}
	}
	
	private void dispatchEvent(UIEvent event) {
		switch(event.type()) {
		case UIEvent.EVENT_KEY -> {
			
		}
		case UIEvent.EVENT_WINDOW_SIZED -> {
			
		}
		case UIEvent.EVENT_MOUSE_INPUT -> {
			
		}
		case UIEvent.EVENT_CURSOR_POSITION -> {
			
		}
		default -> {
			LOGGER.warning("Unknown event: " + event.toString());
		}
		}
		
		
	}
	
}
