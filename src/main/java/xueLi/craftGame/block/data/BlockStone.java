package xueLi.craftGame.block.data;

import java.nio.FloatBuffer;

import xueLi.craftGame.block.BlockData;
import xueLi.craftGame.block.BlockRenderMethod;
import xueLi.craftGame.entity.HitBox;

public class BlockStone extends BlockData {

	public BlockStone() {
		super(1, "Stone");

	}

	@Override
	public HitBox getHitbox() {
		return defaultHitbox;
	}

	@Override
	public int render(FloatBuffer buffer, int x, int y, int z, int dataValue, int face) {
		return BlockRenderMethod.bindDefaultToBuffer(buffer, 3, 0, x, y, z, face);
	}

}
