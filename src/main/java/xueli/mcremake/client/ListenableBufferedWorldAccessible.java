package xueli.mcremake.client;

import java.util.HashMap;
import java.util.function.Consumer;

import org.lwjgl.utils.vector.Vector3i;

import com.flowpowered.nbt.CompoundMap;

import xueli.mcremake.client.events.ModifyBlockEvent;
import xueli.mcremake.core.block.BlockType;
import xueli.mcremake.core.world.BufferedWorldAccessible;
import xueli.mcremake.core.world.WorldAccessible;
import xueli.utils.events.EventBus;

public class ListenableBufferedWorldAccessible extends BufferedWorldAccessible {

	private final EventBus bus;

	private HashMap<Vector3i, BlockType> blockChangeList = new HashMap<>();

	public ListenableBufferedWorldAccessible(WorldAccessible world, EventBus eventBus) {
		super(world);
		this.bus = eventBus;

	}

	public BlockType getBlockImmediate(int x, int y, int z) {
		BlockType changed;
		if ((changed = blockChangeList.get(new Vector3i(x, y, z))) != null)
			return changed;
		return super.getBlock(x, y, z);
	}

	@Override
	public void setBlock(int x, int y, int z, BlockType block) {
		blockChangeList.put(new Vector3i(x, y, z), block);
		commandBuffer.add(() -> {
			world.setBlock(x, y, z, block);
			bus.post(new ModifyBlockEvent(x, y, z));
		});

	}

	@Override
	public void modifyBlockTag(int x, int y, int z, Consumer<CompoundMap> c) {
		commandBuffer.add(() -> {
			world.modifyBlockTag(x, y, z, c);
			bus.post(new ModifyBlockEvent(x, y, z));
		});

	}

	@Override
	public void flush() {
		blockChangeList.clear();
		super.flush();

	}

}
