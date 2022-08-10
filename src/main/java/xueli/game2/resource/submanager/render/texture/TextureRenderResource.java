package xueli.game2.resource.submanager.render.texture;

import java.io.IOException;

import xueli.game2.resource.manager.ChainedResourceManager;
import xueli.game2.resource.submanager.render.RenderResource;

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
		} catch (IOException | NullPointerException e) {
			if (must)
				throw new RuntimeException(e);
			else
				return TextureMissing.get(type);
		}
	}
	
	@Override
	protected void bind(TextureResourceLocation k, Integer v) {
		TextureLoader loader = k.getTextureLoader();
		loader.applyTexture(v);
	}
	
	@Override
	protected void close(TextureResourceLocation k, Integer v) {
		AbstractTextureLoader loader = k.getTextureLoader();
		loader.releaseTexture(v);
	}
	
}
