package xueLi.craftGame.inputListener;

import java.util.concurrent.LinkedBlockingDeque;

import xueLi.craftGame.gui.GUIRenderer;
import xueLi.craftGame.utils.Display;

public class EventManager {

	private static LinkedBlockingDeque<KeyEvent> keyEvents = new LinkedBlockingDeque<KeyEvent>();
	private static LinkedBlockingDeque<MouseButtonEvent> mouseButtonEvents = new LinkedBlockingDeque<MouseButtonEvent>();

	private static boolean listening = false;
	private static Thread mouseListenerThread = new Thread(() -> {
		while (listening) {
			try {
				MouseButtonEvent mouseButtonEvent = mouseButtonEvents.take();
				if (GUIRenderer.currentGui != null)
					GUIRenderer.currentGui.processMouseButtonEvent(mouseButtonEvent);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Display.postDestroyMessage();
			}
		}
	}, "mouseListenerThread");

	private static Thread keyListenerThread = new Thread(() -> {
		while (listening) {
			try {
				KeyEvent keyEvent = keyEvents.take();
				if (GUIRenderer.currentGui != null)
					GUIRenderer.currentGui.processKeyEvent(keyEvent);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Display.postDestroyMessage();
			}

		}
	}, "keyListenerThread");

	public static void startListener() {
		listening = true;
		mouseListenerThread.start();
		keyListenerThread.start();

	}

	public static void addKeyEvent(KeyEvent event) {
		keyEvents.add(event);
	}

	public static void addMouseButtonEvent(MouseButtonEvent event) {
		mouseButtonEvents.add(event);
	}

	public static void stopListener() {
		listening = false;
	}

}
