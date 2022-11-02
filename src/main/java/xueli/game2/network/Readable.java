package xueli.game2.network;

import java.io.IOException;
import java.io.InputStream;

public interface Readable {

	default public int readInteger() throws IOException {
		int v = 0;
		for (int i = 0; i < Integer.BYTES; i++) {
			v |= (((int) readByte()) << (Byte.SIZE * i));
		}
		return v;
	}

	default public short readShort() throws IOException {
		short v = 0;
		for (int i = 0; i < Short.BYTES; i++) {
			v |= (((short) readByte()) << (Byte.SIZE * i));
		}
		return v;
	}

	default public long readLong() throws IOException {
		long v = 0;
		for (int i = 0; i < Long.BYTES; i++) {
			v |= (((long) readByte()) << (Byte.SIZE * i));
		}
		return v;
	}

	default public float readFloat() throws IOException {
		return Float.intBitsToFloat(readInteger());
	}

	default public double readDouble() throws IOException {
		return Double.longBitsToDouble(readLong());
	}

	default public boolean readBoolean() throws IOException {
		return readByte() != 0;
	}

	default public String readString(int length) throws IOException {
		return new String(readBytes(length), CodecConstants.STRING_CHARSET);
	}

	default public byte[] readBytes(int length) throws IOException {
		byte[] bs = new byte[length];
		for (int i = 0; i < length; i++) {
			bs[i] = readByte();
		}
		return bs;
	}

	public byte readByte() throws IOException;

	default InputStream toInputStream() {
		return new InputStream() {
			@Override
			public int read() throws IOException {
				return readByte();
			}
		};
	}

}
