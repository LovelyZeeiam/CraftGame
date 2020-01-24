package xueLi.craftGame.block;

import java.util.HashMap;
import java.util.Map;

import xueLi.craftGame.block.blocks.BlockGrass;
import xueLi.craftGame.block.blocks.BlockStone;
import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.utils.HitBox;

public abstract class Block {

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

	public HitBox getHitBox(BlockPos pos) {
		return getHitbox(pos.getX(), pos.getY(), pos.getZ());
	}

	public abstract HitBox getHitbox(int x, int y, int z);

	private static final HitBox defaultHitbox = new HitBox(0f, 0f, 0f, 1f, 1f, 1f);

	protected HitBox getDefaultBlockHitbox(int x, int y, int z) {
		return defaultHitbox.move(x, y, z);
	}

}
