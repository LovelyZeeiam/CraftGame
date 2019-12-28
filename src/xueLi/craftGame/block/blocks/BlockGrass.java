package xueLi.craftGame.block.blocks;

import java.nio.FloatBuffer;

import xueLi.craftGame.block.Block;
import xueLi.craftGame.block.IBlockDrawMethod;
import xueLi.craftGame.utils.BlockPos;

public class BlockGrass extends Block {

	public BlockGrass() {
		super(2, "Grass Block", new BlockGrassDrawMethod());
	}

	private static class BlockGrassDrawMethod implements IBlockDrawMethod {

		@Override
		public void getDrawData(FloatBuffer vertexbuffer, FloatBuffer texbuffer, int x, int y, int z, int face) {
			IBlockDrawMethod.drawDefaultBlockToBuffer(vertexbuffer, x, y, z, face);
			if(face < 4)
				IBlockDrawMethod.bindTextureToBuffer(texbuffer, 1, 0);
			else if(face == 4)
				IBlockDrawMethod.bindTextureToBuffer(texbuffer, 0, 0);
			else if(face == 5)
				IBlockDrawMethod.bindTextureToBuffer(texbuffer, 2, 0);
			
		}

		@Override
		public void getBlockFrame(FloatBuffer frame, BlockPos pos) {
			IBlockDrawMethod.drawDefaultBlockFrame(frame, pos.getX(),pos.getY(),pos.getZ());
		}

	}
	
}
