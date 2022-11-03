package xueli.game2.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public class ByteBufReadable implements Readable {

	private final ByteBuf buf;

	public ByteBufReadable(ByteBuf buf) {
		this.buf = buf;
	}

	@Override
	public byte readByte() throws IOException {
		return buf.readByte();
	}

}