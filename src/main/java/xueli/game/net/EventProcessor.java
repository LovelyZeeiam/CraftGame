package xueli.game.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import xueli.game.event.Event;

public class EventProcessor {

	private EventProcessor() {
	}

	// 编码
	public static byte[] encodeEvent(Event event) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeObject(event);
			objOut.close();

			return Base64.getEncoder().encodeToString(out.toByteArray()).getBytes(StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	// 解码
	public static Event decodeEvent(byte[] message) {
		try {
			byte[] originData = Base64.getDecoder()
					.decode(new String(message, 0, message.length, StandardCharsets.UTF_8));
			ByteArrayInputStream inputStream = new ByteArrayInputStream(originData);

			ObjectInputStream objIn = new ObjectInputStream(inputStream);
			Object object = objIn.readObject();
			objIn.close();

			return (Event) object;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Event decodeEvent(byte[] message, int offset, int length) {
		try {
			byte[] originData = Base64.getDecoder()
					.decode(new String(message, offset, length, StandardCharsets.UTF_8));
			ByteArrayInputStream inputStream = new ByteArrayInputStream(originData);

			ObjectInputStream objIn = new ObjectInputStream(inputStream);
			Object object = objIn.readObject();
			objIn.close();

			return (Event) object;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}
	
}
