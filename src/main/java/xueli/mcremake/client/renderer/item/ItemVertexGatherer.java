package xueli.mcremake.client.renderer.item;

import com.flowpowered.nbt.CompoundMap;

public interface ItemVertexGatherer {
	
	public void render(float x, float y, CompoundMap tag, ItemRenderer renderer);
	
}
