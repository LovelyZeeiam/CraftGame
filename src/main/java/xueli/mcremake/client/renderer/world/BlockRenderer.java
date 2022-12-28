package xueli.mcremake.client.renderer.world;

import com.flowpowered.nbt.CompoundMap;
import xueli.mcremake.core.world.WorldAccessible;

public interface BlockRenderer {

	public void render(int x, int y, int z, CompoundMap tag, WorldAccessible world, ChunkRenderBuildManager manager);

}
