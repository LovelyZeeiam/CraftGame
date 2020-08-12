package xueLi.craftGame.block;

public class BlockData {

	private String blockName;
	private BlockType type;
	private long destroyTime;
	private int[] textures;

	private BlockListener listener = new BlockListener();

	public BlockData(String blockName, BlockType type, long destroyTime, int[] textures) {
		this.blockName = blockName;
		this.type = type;
		this.destroyTime = destroyTime;
		this.textures = textures;
	}

	public String getBlockName() {
		return blockName;
	}

	public BlockType getType() {
		return type;
	}

	public long getDestroyTime() {
		return destroyTime;
	}

	public int[] getTextures() {
		return textures;
	}

	public void setListener(BlockListener listener) {
		this.listener = listener;

	}

	public BlockListener getListener() {
		return listener;
	}

}
