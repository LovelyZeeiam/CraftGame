package xueli.craftgame.world;

public class ChunkPos {

	private int x, z;

	public ChunkPos(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	@Override
	public String toString() {
		return x + "," + z;
	}

	@Override
	public boolean equals(Object obj) {
		ChunkPos pos = (ChunkPos) obj;
		return pos.x == x && pos.z == z;
	}

}
