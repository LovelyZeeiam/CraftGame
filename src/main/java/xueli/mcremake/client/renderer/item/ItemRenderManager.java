package xueli.mcremake.client.renderer.item;

public interface ItemRenderManager {
	
	public abstract <T extends ItemRenderType> T getRenderType(Class<T> clazz);
	
}
