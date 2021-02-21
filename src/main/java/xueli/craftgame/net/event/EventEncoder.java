package xueli.craftgame.net.event;

import java.util.Base64;

import xueli.utils.Bytes;

public class EventEncoder {

	public static String eventEncode(Event event) {
		byte[] dataByte = Bytes.getBytes(event);
		return Base64.getEncoder().encodeToString(dataByte);
	}

	public static Event eventDecode(String code) {
		byte[] data = Base64.getDecoder().decode(code);
		return (Event) Bytes.getObject(data);
	}

}
