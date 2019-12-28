package xueLi.craftGame.block;

import java.util.HashMap;
import java.util.Map;

import xueLi.craftGame.block.blocks.BlockGrass;
import xueLi.craftGame.block.blocks.BlockStone;

public class Block {

	public static Map<Integer, Block> blockDefault = new HashMap<Integer, Block>();

	static {
		blockDefault.put(1, new BlockStone());
		blockDefault.put(2, new BlockGrass());

	}

	public final int id;
	public final String name;
	public final IBlockDrawMethod method;

	public Block(int id, String name, IBlockDrawMethod method) {
		this.id = id;
		this.name = name;
		this.method = method;

	}

}
