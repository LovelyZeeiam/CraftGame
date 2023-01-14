package xueli.mcremake.client.renderer.item;

import com.flowpowered.nbt.CompoundMap;

import xueli.game2.ecs.ResourceListGeneric;
import xueli.game2.renderer.ui.Gui;
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
	
	public void renderUI(ItemType item, CompoundMap tags, float x, float y, float width, float height, Gui gui) {
		ItemVertexGatherer renderer = item.renderer();
		if(renderer != null) {
			renderer.renderUI(tags, x, y, width, height, this, gui);
		}
		
	}
	
	@Override
	public <T extends ItemRenderType> T getRenderType(Class<T> clazz) {
		return renderTypes.get(clazz);
	}
	
	@Override
	public void reload() {
		renderTypes.values().forEach(ItemRenderType::reload);
	}
	
}
