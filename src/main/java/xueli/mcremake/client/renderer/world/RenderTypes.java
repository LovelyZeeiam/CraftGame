package xueli.mcremake.client.renderer.world;

import xueli.game2.ecs.ResourceListImpl;

public class RenderTypes extends ResourceListImpl {
	
	public RenderTypes(ResourceListImpl renderResources) {
		this.add(new RenderTypeSolid(renderResources));
		
	}
	
}
