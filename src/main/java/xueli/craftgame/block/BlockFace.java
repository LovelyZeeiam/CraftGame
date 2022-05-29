package xueli.craftgame.block;

import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector3i;

public class BlockFace {

	public static final byte FRONT = 0;
	public static final Vector3f FRONT_NORMAL = new Vector3f(0, 0, -1);
	public static final Vector3i FRONT_DIR = new Vector3i(FRONT_NORMAL);
	public static final byte BACK = 1;
	public static final Vector3f BACK_NORMAL = new Vector3f(0, 0, 1);
	public static final Vector3i BACK_DIR = new Vector3i(BACK_NORMAL);
	public static final byte LEFT = 2;
	public static final Vector3f LEFT_NORMAL = new Vector3f(-1, 0, 0);
	public static final Vector3i LEFT_DIR = new Vector3i(LEFT_NORMAL);
	public static final byte RIGHT = 3;
	public static final Vector3f RIGHT_NORMAL = new Vector3f(1, 0, 0);
	public static final Vector3i RIGHT_DIR = new Vector3i(RIGHT_NORMAL);
	public static final byte TOP = 4;
	public static final Vector3f TOP_NORMAL = new Vector3f(0, 1, 0);
	public static final Vector3i TOP_DIR = new Vector3i(TOP_NORMAL);
	public static final byte BOTTOM = 5;
	public static final Vector3f BOTTOM_NORMAL = new Vector3f(0, -1, 0);
	public static final Vector3i BOTTOM_DIR = new Vector3i(BOTTOM_NORMAL);

	public static final byte PART_UP = 0;
	public static final byte PART_DOWN = 1;

	public static final Vector3i[] DIRECTIONS = { FRONT_DIR, BACK_DIR, LEFT_DIR, RIGHT_DIR, TOP_DIR, BOTTOM_DIR };

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
