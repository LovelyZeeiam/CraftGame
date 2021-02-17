package xueli.craftgame.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import xueli.craftgame.net.message.MessageDefine;

public class ByteArrayBuilder {

	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	public ByteArrayBuilder() {

	}

	public ByteArrayBuilder put(byte i) {
		outputStream.write(i);
		return this;
	}

	public ByteArrayBuilder putString(String i) {
		if (i == null)
			return this;

		try {
			outputStream.write(i.getBytes(MessageDefine.STANDARD_CHARSET));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public byte[] get() {
		return outputStream.toByteArray();
	}

}
