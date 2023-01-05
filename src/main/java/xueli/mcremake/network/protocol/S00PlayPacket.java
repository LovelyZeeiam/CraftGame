package xueli.mcremake.network.protocol;

import java.io.IOException;

import xueli.game2.network.Packet;
import xueli.game2.network.Readable;
import xueli.game2.network.Writable;

public class S00PlayPacket extends Packet {

	private float x, y, z;

	public S00PlayPacket(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

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