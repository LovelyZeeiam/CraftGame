package xueli.game2.network;

import java.io.IOException;

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

}
