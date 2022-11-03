package xueli.utils;

import io.netty.buffer.ByteBuf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterInputStream;
import java.util.zip.DeflaterOutputStream;

public class ZLib {

	public static void compress(ByteBuf input, ByteBuf output) throws IOException {
		DeflaterOutputStream out = new DeflaterOutputStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				output.writeByte(b);
			}
		});

		while (input.readableBytes() > 0) {
			out.write(input.readByte());
		}
		out.finish();
		out.flush();

	}

	public static byte[] decompress(ByteBuf input, int size) throws IOException {
		ByteBuf compressed = input.readBytes(size);
		DeflaterInputStream in = new DeflaterInputStream(new InputStream() {
			@Override
			public int read() throws IOException {
				return compressed.readByte();
			}
		});

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		in.transferTo(out);

		return out.toByteArray();
	}

}
