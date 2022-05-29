package xueli.craftgame.event;

public class ChunkPosEvent {

	private int x, z;

	public ChunkPosEvent(int x, int z) {
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
