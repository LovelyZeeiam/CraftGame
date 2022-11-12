package xueli.mcremake.classic.network.protocol;

import xueli.game2.network.Packet;
import xueli.game2.network.PrimitiveCodec;
import xueli.game2.network.Readable;
import xueli.game2.network.Writable;

import java.io.IOException;
import java.util.UUID;

public class C00HelloPacket extends Packet {
	
	public static final String PROCESS_NAME = "client_hello";
	
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
	public String getProcessName() {
		return PROCESS_NAME;
	}

	@Override
	public String toString() {
		return "C00HelloPacket [name=" + name + ", uuid=" + uuid + "]";
	}
	
}
