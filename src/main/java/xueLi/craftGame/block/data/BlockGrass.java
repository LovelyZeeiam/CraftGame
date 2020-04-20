package xueLi.craftGame.block.data;

import java.nio.FloatBuffer;

import xueLi.craftGame.block.BlockData;
import xueLi.craftGame.block.BlockRenderMethod;
import xueLi.craftGame.entity.HitBox;

public class BlockGrass extends BlockData {

	public BlockGrass() {
		super(2, "Grass Block");

	}

	@Override
	public HitBox getHitbox() {
		return defaultHitbox;
	}

	@Override
	public int render(FloatBuffer buffer, int x, int y, int z, int dataValue, int face) {
		if (face < 4)
			return BlockRenderMethod.bindDefaultToBuffer(buffer, 1, 0, x, y, z, face);
		else if (face == 4)
			return BlockRenderMethod.bindDefaultToBuffer(buffer, 0, 0, x, y, z, face);
		else if (face == 5)
			return BlockRenderMethod.bindDefaultToBuffer(buffer, 2, 0, x, y, z, face);
		return 0;
	}

}
