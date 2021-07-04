package xueli.craftgame.block;

import xueli.craftgame.world.Dimension;
import xueli.craftgame.world.Tile;
import xueli.game.utils.FloatList;
import xueli.utils.Asserts;

public class AbstractAlphaBlock extends AbstractBlock {

	public AbstractAlphaBlock(String namespace, String nameInternational, String... textureNames) {
		super(namespace, nameInternational, textureNames);

		isAlpha = true;

	}

	@Override
	public int getRenderCubeData(FloatList buffer, int x, int y, int z, byte face, Dimension dimension) {
		int vertCount = 0;
		if (!Asserts.assertNull(dimension.getBlock(x - 1, y, z), this::assertDraw)) {
			vertCount += super.getRenderCubeData(buffer, x, y, z, BlockFace.LEFT, dimension);
		}
		if (!Asserts.assertNull(dimension.getBlock(x + 1, y, z), this::assertDraw)) {
			vertCount += super.getRenderCubeData(buffer, x, y, z, BlockFace.RIGHT, dimension);
		}
		if (!Asserts.assertNull(dimension.getBlock(x, y + 1, z), this::assertDraw)) {
			vertCount += super.getRenderCubeData(buffer, x, y, z, BlockFace.TOP, dimension);
		}
		if (!Asserts.assertNull(dimension.getBlock(x, y - 1, z), this::assertDraw)) {
			vertCount += super.getRenderCubeData(buffer, x, y, z, BlockFace.BOTTOM, dimension);
		}
		if (!Asserts.assertNull(dimension.getBlock(x, y, z + 1), this::assertDraw)) {
			vertCount += super.getRenderCubeData(buffer, x, y, z, BlockFace.BACK, dimension);
		}
		if (!Asserts.assertNull(dimension.getBlock(x, y, z - 1), this::assertDraw)) {
			vertCount += super.getRenderCubeData(buffer, x, y, z, BlockFace.FRONT, dimension);
		}
		return vertCount;
	}

	private boolean assertDraw(Tile t) {
		return (!t.getBase().isAlpha && t.getBase().isComplete)
				|| t.getBase().getNamespace().equals(this.getNamespace());
	}

	@Override
	public int getRenderModelViewData(FloatList buffer) {
		for (byte i = 0; i < 6; i++) {
			super.getRenderCubeData(buffer, 0, 0, 0, i, null);
		}
		return 36;
	}

}
