package xueLi.craftGame.block;

import xueLi.craftGame.entity.HitBox;
import xueLi.craftGame.utils.BlockPos;

public abstract class Block {

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
