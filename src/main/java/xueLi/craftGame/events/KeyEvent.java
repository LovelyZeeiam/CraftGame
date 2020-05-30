package xueLi.craftGame.events;

public class KeyEvent {

	public int key;
	public int scancode;
	public int action;

	public KeyEvent(int key, int scancode, int action) {
		this.key = key;
		this.scancode = scancode;
		this.action = action;
	}

}
