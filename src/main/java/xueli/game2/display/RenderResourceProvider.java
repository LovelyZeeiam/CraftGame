package xueli.game2.display;

import xueli.game2.resource.manager.ResourceManager;
import xueli.game2.resource.render.texture.TextureRenderResource;

public interface RenderResourceProvider {

	public ResourceManager getResourceManager();

	public TextureRenderResource getTextureRenderResource();

}
