package xueli.mcremake.client.renderer.item;

import com.flowpowered.nbt.CompoundMap;

public interface ItemVertexGatherer {
	
	public void render(CompoundMap tags, float x, float y, float width, float height, ItemRenderManager manager);
	
}
