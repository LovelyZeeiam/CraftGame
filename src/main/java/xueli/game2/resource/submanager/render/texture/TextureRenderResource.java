package xueli.game2.resource.submanager.render.texture;

import java.io.IOException;

import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.manager.ChainedResourceManager;
import xueli.game2.resource.submanager.render.RenderResource;

public class TextureRenderResource extends RenderResource<ResourceLocation, Texture> {
	
	private static final TextureLoaderLegacy LEGACY_LOADER = new TextureLoaderLegacy();
	
	public TextureRenderResource(ChainedResourceManager manager) {
		super(manager);

	}
	
	@Override
	protected Texture doRegister(ResourceLocation k, boolean must) {
		
		try {
			return LEGACY_LOADER.registerTexture(k, getUpperResourceManager());
		} catch (IOException | NullPointerException e) {
			if (must)
				throw new RuntimeException(e);
			else
				return TextureMissing.get(LEGACY_LOADER);
		}
	}
	
	@Override
	protected void close(ResourceLocation k, Texture v) {
		LEGACY_LOADER.releaseTexture(v);
	}
	
}
