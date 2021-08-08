package xueli.utils.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ByteBufferInputStream extends InputStream {

	private final ByteBuffer in;

	public ByteBufferInputStream(ByteBuffer in) {
		this.in = in;

	}

	@Override
	public int read() throws IOException {
		return in.get();
	}

}
