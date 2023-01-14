package xueli.mcremake.registry;

import xueli.game2.ecs.ResourceListGeneric;
import xueli.game2.ecs.ResourceListImpl;
import xueli.mcremake.client.renderer.world.ChunkRenderType;
import xueli.mcremake.client.renderer.world.RenderTypeSolid;

public class BlockRenderTypes extends ResourceListGeneric<ChunkRenderType> {
	
	public BlockRenderTypes(ResourceListImpl renderResources) {
		this.add(new RenderTypeSolid(renderResources));
	}
	
}
