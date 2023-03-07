package xueli.mcremake.client.renderer.item;

import com.flowpowered.nbt.CompoundMap;

import xueli.game2.renderer.ui.Gui;

public interface ItemVertexGatherer {
	
	public void renderUI(CompoundMap tags, float x, float y, float width, float height, ItemRenderManager manager, Gui gui);
	
}