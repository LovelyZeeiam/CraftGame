package xueli.game2.network;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class ByteBufWritable implements Writable {

	private final ByteBuf buf;

	public ByteBufWritable(ByteBuf buf) {
		this.buf = buf;

	}

	@Override
	public void writeByte(byte b) throws IOException {
		buf.writeByte(b);

	}

}
