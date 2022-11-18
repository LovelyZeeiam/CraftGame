package xueli.game2.resource.submanager.render.texture;

import xueli.game2.renderer.ui.MyGui;
import xueli.game2.resource.manager.ChainedResourceManager;
import xueli.game2.resource.submanager.render.RenderResource;

import java.io.IOException;

public class TextureRenderResource extends RenderResource<TextureResourceLocation, Integer> {

	final MyGui gui;

	public TextureRenderResource(MyGui gui, ChainedResourceManager manager) {
		super(manager);
		this.gui = gui;

	}
	
	@Override
	protected Integer doRegister(TextureResourceLocation k, boolean must) {
		AbstractTextureLoader loader = k.type().getLoader(this);
		try {
			return loader.registerTexture(k.location(), getUpperResourceManager());
		} catch (IOException | NullPointerException e) {
			if (must)
				throw new RuntimeException(e);
			else
				return TextureMissing.get(loader);
		}
	}
	
	@Override
	protected void close(TextureResourceLocation k, Integer v) {
		TextureLoader loader = k.type().getLoader(this);
		loader.releaseTexture(v);
	}
	
}
