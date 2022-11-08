package xueli.game2.network;

import java.io.IOException;

import xueli.game2.network.processor.PacketProcessor;

public abstract class Packet {

	public Packet() {
	}

	public Packet(Readable buf) {
		try {
			read(buf);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public abstract void read(Readable buf) throws IOException;
	public abstract void write(Writable buf) throws IOException;

	public abstract String getProcessName();

	public void process(PacketProcessor processor) {
		processor.doProcess(getProcessName(), this);

	}
	
}
