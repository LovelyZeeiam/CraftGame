package xueli.mcremake.classic.network.protocol;

import java.io.IOException;

import xueli.game2.network.Packet;
import xueli.game2.network.Readable;
import xueli.game2.network.Writable;

public class S00PlayPacket extends Packet {
	
	public static final String PROCESS_NAME = "server_hello";
	
	public S00PlayPacket(Readable buf) {
		super(buf);
	}
	
	@Override
	public void read(Readable buf) throws IOException {
		
		
	}

	@Override
	public void write(Writable buf) throws IOException {
		
		
	}

	@Override
	public String getProcessName() {
		return PROCESS_NAME;
	}

}
