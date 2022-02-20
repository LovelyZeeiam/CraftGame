package xueli.craftgame.block;

import com.flowpowered.nbt.CompoundMap;
import xueli.craftgame.world.Dimension;
import xueli.game.utils.FloatList;
import xueli.utils.Asserts;

public class AbstractAlphaBlock extends AbstractBlock {

	public AbstractAlphaBlock(String namespace, String nameInternational, String... textureNames) {
		super(namespace, nameInternational, textureNames);

		isAlpha = true;

	}

	@Override
	public int getRenderCubeData(FloatList buffer, int x, int y, int z, byte face, CompoundMap tag, Dimension dimension) {
		int vertCount = 0;
		if (!Asserts.assertNull(dimension.getBlock(x - 1, y, z), this::assertDraw)) {
			vertCount += super.getRenderCubeData(buffer, x, y, z, BlockFace.LEFT,tag, dimension);
		}
		if (!Asserts.assertNull(dimension.getBlock(x + 1, y, z), this::assertDraw)) {
			vertCount += super.getRenderCubeData(buffer, x, y, z, BlockFace.RIGHT,tag, dimension);
		}
		if (!Asserts.assertNull(dimension.getBlock(x, y + 1, z), this::assertDraw)) {
			vertCount += super.getRenderCubeData(buffer, x, y, z, BlockFace.TOP,tag, dimension);
		}
		if (!Asserts.assertNull(dimension.getBlock(x, y - 1, z), this::assertDraw)) {
			vertCount += super.getRenderCubeData(buffer, x, y, z, BlockFace.BOTTOM,tag, dimension);
		}
		if (!Asserts.assertNull(dimension.getBlock(x, y, z + 1), this::assertDraw)) {
			vertCount += super.getRenderCubeData(buffer, x, y, z, BlockFace.BACK,tag, dimension);
		}
		if (!Asserts.assertNull(dimension.getBlock(x, y, z - 1), this::assertDraw)) {
			vertCount += super.getRenderCubeData(buffer, x, y, z, BlockFace.FRONT,tag, dimension);
		}
		return vertCount;
	}

	private boolean assertDraw(BlockBase t) {
		return (!t.isAlpha && t.isComplete)
				|| t.getNamespace().equals(this.getNamespace());
	}

	@Override
	public int getRenderModelViewData(FloatList buffer) {
		for (byte i = 0; i < 6; i++) {
			super.getRenderCubeData(buffer, 0, 0, 0, i,null, null);
		}
		return 36;
	}

}
