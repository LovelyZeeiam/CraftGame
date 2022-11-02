package xueli.game2.network;

import java.io.IOException;
import java.io.OutputStream;

public interface Writable {

	default public void writeInteger(int i) throws IOException {
		for (int j = 0; j < Integer.BYTES; j++) {
			writeByte((byte) (i & 0xFF));
			i >>>= Byte.SIZE;
		}
	}

	default public void writeShort(short i) throws IOException {
		for (int j = 0; j < Short.BYTES; j++) {
			writeByte((byte) (i & 0xFF));
			i >>>= Byte.SIZE;
		}
	}

	default public void writeLong(long i) throws IOException {
		for (int j = 0; j < Long.BYTES; j++) {
			writeByte((byte) (i & 0xFF));
			i >>>= Byte.SIZE;
		}
	}

	default public void writeFloat(float f) throws IOException {
		writeInteger(Float.floatToIntBits(f));
	}

	default public void writeDouble(double d) throws IOException {
		writeLong(Double.doubleToLongBits(d));
	}

	default public void writeBoolean(boolean b) throws IOException {
		writeByte((byte) (b ? 1 : 0));
	}

	default public void writeString(String s) throws IOException {
		writeBytes(s.getBytes(CodecConstants.STRING_CHARSET));
	}

	default public void writeBytes(byte[] b) throws IOException {
		for (int i = 0; i < b.length; i++) {
			writeByte(b[i]);
		}
	}

	public void writeByte(byte b) throws IOException;

	default public OutputStream toOutputStream() {
		return new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				writeByte((byte) (b & 0xFF));
			}
		};
	}

}
