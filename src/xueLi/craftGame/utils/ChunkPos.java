package xueLi.craftGame.utils;

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

}
