package xueli.mcremake.registry;

import java.util.HashMap;

import com.flowpowered.nbt.CompoundMap;

import xueli.game2.ecs.ResourceListImpl;
import xueli.game2.renderer.ui.Gui;
import xueli.game2.resource.submanager.render.texture.Texture;
import xueli.mcremake.client.renderer.item.ItemRenderType;
import xueli.mcremake.core.block.BlockType;

public class ItemRenderTypeRegularBlock implements ItemRenderType {
	
	private final BlockIconGenerator iconGenerator;
	private final HashMap<Texture, Integer> guiImages = new HashMap<>();
	
	public ItemRenderTypeRegularBlock(ResourceListImpl renderResources) {
		iconGenerator = renderResources.get(BlockIconGenerator.class);
	}
	
	public void renderUI(BlockType block, CompoundMap tags, float x, float y, float width, float height, Gui gui) {
		int image = this.guiImages.computeIfAbsent(iconGenerator.getTextureId(block.namespace()), gui::registerImage);
//		System.out.println(image);
		gui.drawImage(x, y, width, height, 1.0f, image);
	}
	
	@Override
	public void reload() {
		// When reload GUI class will recreate NanoVG instance when our registered image gets deleted
		// So here we just clear the HashMap
		this.guiImages.clear();
		
	}
	
	@Override
	public void release() {
	}
	
}
