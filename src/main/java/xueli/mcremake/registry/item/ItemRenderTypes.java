package xueli.mcremake.registry.item;

import xueli.game2.ecs.ResourceListGeneric;
import xueli.game2.ecs.ResourceListImpl;
import xueli.mcremake.client.renderer.item.ItemRenderType;

public class ItemRenderTypes extends ResourceListGeneric<ItemRenderType> {
	
	public ItemRenderTypes(ResourceListImpl renderResources) {
		this.add(new ItemRenderTypeRegularBlock(renderResources));
		
	}

}
