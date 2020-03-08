package xueLi.craftGame.utils;

public class BlockPos {

	private int x, y, z;

	public BlockPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	@Override
	public String toString() {
		return x + "," + y + "," + z;
	}
	
	public static Vector getBlockPosInChunk(Vector pos,ChunkPos cpos) {
		return new Vector(pos.x - cpos.getX() * 16,pos.y,pos.z - cpos.getZ() * 16,pos.rotX,pos.rotY,pos.rotZ);
	}

}
