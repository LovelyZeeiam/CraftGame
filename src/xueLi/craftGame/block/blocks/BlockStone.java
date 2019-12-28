package xueLi.craftGame.block.blocks;

import java.nio.FloatBuffer;

import xueLi.craftGame.block.Block;
import xueLi.craftGame.block.IBlockDrawMethod;
import xueLi.craftGame.utils.BlockPos;

public class BlockStone extends Block {

	public BlockStone() {
		super(1, "Stone", new BlockStoneDrawMethod());
	}

	private static class BlockStoneDrawMethod implements IBlockDrawMethod {

		@Override
		public void getDrawData(FloatBuffer vertexbuffer, FloatBuffer texbuffer, int x, int y, int z, int face) {
			IBlockDrawMethod.drawDefaultBlockToBuffer(vertexbuffer, x, y, z, face);
			IBlockDrawMethod.bindTextureToBuffer(texbuffer, 3, 0);

		}
		
		@Override
		public void getBlockFrame(FloatBuffer frame, BlockPos pos) {
			IBlockDrawMethod.drawDefaultBlockFrame(frame, pos.getX(),pos.getY(),pos.getZ());
		}
		
		
	}

}
