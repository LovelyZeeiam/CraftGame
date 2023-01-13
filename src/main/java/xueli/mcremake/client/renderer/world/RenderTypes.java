package xueli.mcremake.client.renderer.world;

import xueli.game2.ecs.ResourceListGeneric;
import xueli.game2.ecs.ResourceListImpl;

public class RenderTypes extends ResourceListGeneric<ChunkRenderType> {
	
	public RenderTypes(ResourceListImpl renderResources) {
		this.add(new RenderTypeSolid(renderResources));
		
	}
	
}
