package xueli.gui;

public record UIEvent(int type, Object data) {

	public static final int EVENT_KEY = 0;
	public static final int EVENT_WINDOW_SIZED = 1;
	public static final int EVENT_MOUSE_INPUT = 2;
	public static final int EVENT_CURSOR_POSITION = 3;

}
