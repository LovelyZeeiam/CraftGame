package xueli.mcremake.client.renderer.item;

import com.flowpowered.nbt.CompoundMap;

import xueli.game2.ecs.ResourceListGeneric;
import xueli.game2.resource.ResourceHolder;
import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.core.item.ItemType;

public class ItemRenderMaster implements ResourceHolder, ItemRenderManager {
	
	@SuppressWarnings("unused")
	private final CraftGameClient ctx;
	private final ResourceListGeneric<ItemRenderType> renderTypes;
	
	public ItemRenderMaster(ResourceListGeneric<ItemRenderType> renderTypes, CraftGameClient ctx) {
		this.ctx = ctx;
		this.renderTypes = renderTypes;
		
	}
	
	public void render(ItemType item, CompoundMap tags, float x, float y, float width, float height) {
		ItemVertexGatherer renderer = item.renderer();
		if(renderer != null) {
			renderer.render(tags, x, y, width, height, this);
		}
		
	}
	
	@Override
	public <T extends ItemRenderType> T getRenderType(Class<T> clazz) {
		return renderTypes.get(clazz);
	}
	
	@Override
	public void reload() {
	}
	
}
