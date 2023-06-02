package xueli.gui;

// I've glanced at sources of QT as well :}
public record SizeHint(int type, int width, int height) {
	
	public static final int POLICY_FIXED = 0;
	public static final int POLICY_MINIMUM = 1;
	public static final int POLICY_MAXIMUM = 2;
	public static final int POLICY_PREFERED = 3;
	public static final int POLICY_EXPANDING = 4;
	public static final int POLICY_MINIMUM_EXPANDING = 5;
	public static final int POLICY_IGNORED = 6;
	
}
