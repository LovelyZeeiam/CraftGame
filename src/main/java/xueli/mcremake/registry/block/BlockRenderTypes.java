package xueli.mcremake.registry.block;

import xueli.game2.ecs.ResourceListGeneric;
import xueli.game2.ecs.ResourceListImpl;
import xueli.mcremake.client.renderer.world.ChunkRenderType;
import xueli.mcremake.client.renderer.world.RenderTypeAlpha;
import xueli.mcremake.client.renderer.world.RenderTypeSolid;

public class BlockRenderTypes extends ResourceListGeneric<ChunkRenderType> {
	
	public BlockRenderTypes(ResourceListImpl renderResources) {
		this.add(new RenderTypeSolid(renderResources));
		this.add(new RenderTypeAlpha(renderResources));
	}
	
}
