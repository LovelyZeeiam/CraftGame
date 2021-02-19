package xueli.craftgame.block;

import xueli.craftgame.CraftGame;

public class Block {

	private BlockData data;
	private long details = 0;

	public Block(BlockData data, long details) {
		this.data = data;
		this.details = details;

	}

	public Block(String namespace) {
		this.data = CraftGame.INSTANCE_CRAFT_GAME.getBlockResource().getBlockData(namespace);

	}

	public Block(String namespace, long details) {
		this.data = CraftGame.INSTANCE_CRAFT_GAME.getBlockResource().getBlockData(namespace);
		this.details = details;

	}

	public BlockData getData() {
		return data;
	}

	public long getDetails() {
		return details;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + (int) (details ^ (details >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (details != other.details)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Block [data=" + data + ", details=" + details + "]";
	}

}
