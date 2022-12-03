package xueli.mcremake.classic.network.protocol;

import xueli.game2.network.Packet;
import xueli.game2.network.PrimitiveCodec;
import xueli.game2.network.Readable;
import xueli.game2.network.Writable;

import java.io.IOException;

public class S00PlayPacket extends Packet {

	private float x, y, z;
	
	public S00PlayPacket(Readable buf) {
		super(buf);
	}
	
	@Override
	public void read(Readable buf) throws IOException {
		this.x = buf.readFloat();
		this.y = buf.readFloat();
		this.z = buf.readFloat();

	}

	@Override
	public void write(Writable buf) throws IOException {
		buf.writeFloat(this.x);
		buf.writeFloat(this.y);
		buf.writeFloat(this.z);

	}

}
