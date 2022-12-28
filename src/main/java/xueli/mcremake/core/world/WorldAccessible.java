package xueli.mcremake.core.world;

import com.flowpowered.nbt.CompoundMap;
import xueli.mcremake.core.block.BlockType;

import java.util.function.Consumer;

public interface WorldAccessible {

	public BlockType getBlock(int x, int y, int z);
	public CompoundMap getBlockTag(int x, int y, int z);

	public void setBlock(int x, int y, int z, BlockType block);
	public void modifyBlockTag(int x, int y, int z, Consumer<CompoundMap> c);

}
