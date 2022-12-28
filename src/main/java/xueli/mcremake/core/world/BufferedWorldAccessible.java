package xueli.mcremake.core.world;

import com.flowpowered.nbt.CompoundMap;
import xueli.mcremake.core.block.BlockType;

import java.util.ArrayList;
import java.util.function.Consumer;

public class BufferedWorldAccessible implements WorldAccessible {

	private final WorldAccessible world;

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

	private final ArrayList<Runnable> commandBuffer = new ArrayList<>();

	@Override
	public void setBlock(int x, int y, int z, BlockType block) {
		commandBuffer.add(() -> world.setBlock(x, y, z, block));
	}

	@Deprecated
	@Override
	public CompoundMap createBlockTag(int x, int y, int z) {
		return world.createBlockTag(x, y, z);
	}

	public void createBlockTag(int x, int y, int z, Consumer<CompoundMap> c) {
		commandBuffer.add(() -> {
			CompoundMap map = world.createBlockTag(x, y, z);
			c.accept(map);
		});
	}

	public void flush() {
		commandBuffer.forEach(Runnable::run);
		commandBuffer.clear();

	}

}
