package xueLi.craftGame.block;

import java.nio.FloatBuffer;

import xueLi.craftGame.entity.HitBox;
import xueLi.craftGame.utils.BlockPos;

public class BlockDirt extends Block {

	private static BlockDrawMethod drawMethod = new BlockDrawMethod();

	public BlockDirt() {
		super(3, "Dirt", drawMethod);
	}

	private static class BlockDrawMethod implements IBlockDrawMethod {

		@Override
		public void getDrawData(FloatBuffer buffer, int x, int y, int z, int face) {
			IBlockDrawMethod.bindDefaultToBuffer(buffer, 2, 0, x, y, z, face);
		}

		@Override
		public void getBlockFrame(FloatBuffer frame, BlockPos pos) {
			IBlockDrawMethod.drawDefaultBlockFrame(frame, pos.getX(), pos.getY(), pos.getZ());
		}

	}

	@Override
	public HitBox getHitbox(int x, int y, int z) {
		return super.getDefaultBlockHitbox(x, y, z);
	}
	
}
