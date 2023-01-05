package xueli.mcremake.core.world;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.flowpowered.nbt.CompoundMap;

import xueli.mcremake.core.block.BlockType;

public class BufferedWorldAccessible implements WorldAccessible {

	protected final WorldAccessible world;

	public BufferedWorldAccessible(WorldAccessible world) {
		this.world = world;
	}

	@Override
	public BlockType getBlock(int x, int y, int z) {
		return world.getBlock(x, y, z);
	}

	@Override
	public CompoundMap getBlockTag(int x, int y, int z) {
		return world.getBlockTag(x, y, z);
	}

	protected final ArrayList<Runnable> commandBuffer = new ArrayList<>();

	@Override
	public void setBlock(int x, int y, int z, BlockType block) {
		commandBuffer.add(() -> world.setBlock(x, y, z, block));
	}

	@Override
	public void modifyBlockTag(int x, int y, int z, Consumer<CompoundMap> c) {
		commandBuffer.add(() -> {
			world.modifyBlockTag(x, y, z, c);
		});
	}

	public void flush() {
		commandBuffer.forEach(Runnable::run);
		commandBuffer.clear();

	}

}
