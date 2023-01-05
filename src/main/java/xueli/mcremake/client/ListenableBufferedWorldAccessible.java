package xueli.mcremake.client;

import java.util.function.Consumer;

import com.flowpowered.nbt.CompoundMap;

import xueli.mcremake.core.block.BlockType;
import xueli.mcremake.core.world.BufferedWorldAccessible;
import xueli.mcremake.core.world.WorldAccessible;
import xueli.utils.EventBus;

public class ListenableBufferedWorldAccessible extends BufferedWorldAccessible {

	private final EventBus bus;

	public ListenableBufferedWorldAccessible(WorldAccessible world, EventBus eventBus) {
		super(world);
		this.bus = eventBus;

	}

	@Override
	public void setBlock(int x, int y, int z, BlockType block) {
		commandBuffer.add(() -> {
			world.setBlock(x, y, z, block);
			bus.post(new WorldEvents.ModifyBlockEvent(x, y, z));
		});
	}

	@Override
	public void modifyBlockTag(int x, int y, int z, Consumer<CompoundMap> c) {
		commandBuffer.add(() -> {
			world.modifyBlockTag(x, y, z, c);
			bus.post(new WorldEvents.ModifyBlockEvent(x, y, z));
		});
	}

}
