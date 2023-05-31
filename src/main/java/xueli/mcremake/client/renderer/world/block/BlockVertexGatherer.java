package xueli.mcremake.client.renderer.world.block;

import com.flowpowered.nbt.CompoundMap;

import xueli.mcremake.client.renderer.world.ChunkRenderBuildManager;
import xueli.mcremake.core.world.WorldAccessible;

public interface BlockVertexGatherer {

	public void render(int x, int y, int z, CompoundMap tag, WorldAccessible world, ChunkRenderBuildManager manager);

	default public void renderIcon(ChunkRenderBuildManager manager) {
		this.render(0, 0, 0, null, null, manager);
	}

}
