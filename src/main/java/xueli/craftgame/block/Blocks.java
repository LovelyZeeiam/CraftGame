package xueli.craftgame.block;

import java.util.HashMap;

public class Blocks {

	private HashMap<String, Block> blocks = new HashMap<>();

	public Blocks() {

	}

	public Block getBlockByNamespace(String namespace) {
		return blocks.get(namespace);
	}

	public void registerBlock(Block block) {
		this.blocks.put(block.getNamespace(), block);
	}

}
