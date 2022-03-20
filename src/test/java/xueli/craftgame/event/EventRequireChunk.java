package xueli.craftgame.event;

import xueli.craftgame.world.SubChunk;

public class EventRequireChunk extends FutureEvent<SubChunk> {

	private int x, z;

	public EventRequireChunk(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

}
