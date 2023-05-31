package xueli.mcremake.network.protocol;

import java.io.IOException;
import java.util.UUID;

import xueli.game2.network.Packet;
import xueli.game2.network.PrimitiveCodec;
import xueli.game2.network.Readable;
import xueli.game2.network.Writable;

public class C00HelloPacket extends Packet {

	private String name;
	private UUID uuid;

	public C00HelloPacket(Readable buf) {
		super(buf);
	}

	public C00HelloPacket(String name, UUID uuid) {
		this.name = name;
		this.uuid = uuid;
	}

	@Override
	public void read(Readable buf) throws IOException {
		this.name = PrimitiveCodec.STRING.read(buf);
		this.uuid = PrimitiveCodec.UUID.read(buf);

	}

	@Override
	public void write(Writable buf) throws IOException {
		PrimitiveCodec.STRING.write(name, buf);
		PrimitiveCodec.UUID.write(uuid, buf);

	}

	@Override
	public String toString() {
		return "C00HelloPacket [name=" + name + ", uuid=" + uuid + "]";
	}

}
