package xueli.mcremake.client.renderer.item;

import xueli.game2.resource.ResourceHolder;

public interface ItemRenderType extends ResourceHolder {
	
//	public void render(CompoundMap tags, float x, float y, float width, float height, ItemRenderManager manager);
	
	public void release();
	
}
