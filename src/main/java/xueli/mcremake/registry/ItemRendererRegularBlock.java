package xueli.mcremake.registry;

import com.flowpowered.nbt.CompoundMap;

import xueli.game2.renderer.ui.Gui;
import xueli.mcremake.client.renderer.item.ItemRenderManager;
import xueli.mcremake.client.renderer.item.ItemVertexGatherer;
import xueli.mcremake.core.block.BlockType;

public class ItemRendererRegularBlock implements ItemVertexGatherer {
	
	private final BlockType blockType;
	
	public ItemRendererRegularBlock(BlockType blockType) {
		this.blockType = blockType;
	}
	
	@Override
	public void renderUI(CompoundMap tags, float x, float y, float width, float height, ItemRenderManager manager, Gui gui) {
		var renderType = manager.getRenderType(ItemRenderTypeRegularBlock.class);
		renderType.renderUI(blockType, tags, x, y, width, height, gui);
		
	}

}
