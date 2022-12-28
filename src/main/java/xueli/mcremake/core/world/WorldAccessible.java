package xueli.mcremake.core.world;

import com.flowpowered.nbt.CompoundMap;
import xueli.mcremake.core.block.BlockType;

public interface WorldAccessible {

	public BlockType getBlock(int x, int y, int z);
	public CompoundMap getBlockTag(int x, int y, int z);

	public void setBlock(int x, int y, int z, BlockType block);
	public CompoundMap createBlockTag(int x, int y, int z);

}
