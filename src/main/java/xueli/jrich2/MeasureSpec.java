package xueli.jrich2;

public class MeasureSpec {
	
	public final Mode mode;
	public final int value;
	
	public MeasureSpec(Mode mode, int value) {
		this.mode = mode;
		this.value = value;
	}

	public static enum Mode {
		UNLIMITED, EXACTLY, MAX_LIMITED
	}
	
}
