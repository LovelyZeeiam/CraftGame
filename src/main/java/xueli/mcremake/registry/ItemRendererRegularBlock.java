package xueli.mcremake.registry;

import com.flowpowered.nbt.CompoundMap;

import xueli.mcremake.client.renderer.item.ItemRenderManager;
import xueli.mcremake.client.renderer.item.ItemVertexGatherer;
import xueli.mcremake.core.block.BlockType;

public class ItemRendererRegularBlock implements ItemVertexGatherer {
	
	private final BlockType blockType;
	
	public ItemRendererRegularBlock(BlockType blockType) {
		this.blockType = blockType;
	}
	
	@Override
	public void render(CompoundMap tags, float x, float y, float width, float height, ItemRenderManager manager) {
		var renderType = manager.getRenderType(ItemRenderTypeRegularBlock.class);
		renderType.render(blockType, tags, x, y, width, height);
		
	}

}
