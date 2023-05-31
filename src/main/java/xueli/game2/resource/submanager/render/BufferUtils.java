package xueli.game2.resource.submanager.render;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Is this serious? Can the buffer be borrowed or transfered just like Rust do?
 */
public class BufferUtils {

	private static ArrayList<Buffer> bufferStack = new ArrayList<>();

	private BufferUtils() {
	}

	public static ByteBuffer createByteBuffer(int capacity) {
		ByteBuffer buffer = org.lwjgl.BufferUtils.createByteBuffer(capacity);
		bufferStack.add(buffer);
		return buffer;
	}

	public static void free(Buffer buffer) {
		bufferStack.remove(buffer);
	}

}
