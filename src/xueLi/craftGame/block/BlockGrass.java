package xueLi.craftGame.block;

import java.nio.FloatBuffer;

import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.utils.HitBox;

public class BlockGrass extends Block {

	private static BlockDrawMethod drawMethod = new BlockDrawMethod();
	
	public BlockGrass() {
		super(2, "Grass Block", drawMethod);
	}

	private static class BlockDrawMethod implements IBlockDrawMethod {

		@Override
		public void getDrawData(FloatBuffer buffer, int x, int y, int z, int face) {
			if (face < 4)
				IBlockDrawMethod.bindDefaultToBuffer(buffer, 1, 0, x, y, z, face);
			else if (face == 4)
				IBlockDrawMethod.bindDefaultToBuffer(buffer, 0, 0, x, y, z, face);
			else if (face == 5)
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
