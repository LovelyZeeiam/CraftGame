package xueli.craftgame.resource.render.texture;

import java.io.IOException;
import xueli.craftgame.resource.manager.ChainedResourceManager;
import xueli.craftgame.resource.render.RenderResource;

public class TextureRenderResource extends RenderResource<TextureResourceLocation, Integer> {
	
	public TextureRenderResource(ChainedResourceManager manager) {
		super(manager);
	}
	
	@Override
	protected Integer register(TextureResourceLocation k, boolean must) {
		TextureLoader loader = k.getTextureLoader();
		TextureType type = k.type();
		try {
			return loader.registerTexture(k, getUpperResourceManager());
		} catch (IOException e) {
			if (must)
				throw new RuntimeException(e);
			else
				return TextureMissing.get(type);
		}
	}
	
	@Override
	protected void close(TextureResourceLocation k, Integer v) {
		AbstractTextureLoader loader = k.getTextureLoader();
		loader.releaseTexture(v);
	}
	
}
