package xueli.game2.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteBufferWritable implements Writable {

	private final ByteArrayOutputStream out = new ByteArrayOutputStream();

	@Override
	public void writeByte(byte b) throws IOException {
		out.write(b);
	}

	public byte[] toByteArray() {
		return out.toByteArray();
	}

}
