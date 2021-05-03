package xueli.craftgame.block.data;

public class BlockFace {

	public static final byte FRONT = 0;
	public static final byte BACK = 1;
	public static final byte LEFT = 2;
	public static final byte RIGHT = 3;
	public static final byte TOP = 4;
	public static final byte BOTTOM = 5;

	public static final byte PART_UP = 0;
	public static final byte PART_DOWN = 1;

	public static String getFacingDescription(byte i) {
		return switch (i) {
		case FRONT: {
			yield "FRONT";
		}
		case BACK: {
			yield "BACK";
		}
		case LEFT: {
			yield "LEFT";
		}
		case RIGHT: {
			yield "RIGHT";
		}
		case TOP: {
			yield "TOP";
		}
		case BOTTOM: {
			yield "BOTTOM";
		}
		default:
			yield "UNKNOWN";
		};
	}

	public static String getPartDescription(byte i) {
		return switch (i) {
		case PART_UP: {
			yield "PART_UP";
		}
		case PART_DOWN: {
			yield "PART_DOWN";
		}
		default:
			yield "UNKNOWN";
		};
	}

}
