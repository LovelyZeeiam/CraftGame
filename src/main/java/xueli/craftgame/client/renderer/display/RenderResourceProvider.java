package xueli.craftgame.client.renderer.display;

import xueli.craftgame.resource.manager.ResourceManager;
import xueli.craftgame.resource.render.texture.TextureRenderResource;

public interface RenderResourceProvider {

	public ResourceManager getResourceManager();

	public TextureRenderResource getTextureRenderResource();

}
