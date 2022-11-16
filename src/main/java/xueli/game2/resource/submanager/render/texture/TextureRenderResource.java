package xueli.game2.resource.submanager.render.texture;

import xueli.game2.renderer.ui.MyGui;
import xueli.game2.resource.manager.ChainedResourceManager;
import xueli.game2.resource.submanager.render.RenderResource;

import java.io.IOException;

public class TextureRenderResource extends RenderResource<TextureResourceLocation, Integer> {

	private final MyGui gui;

	public TextureRenderResource(MyGui gui, ChainedResourceManager manager) {
		super(manager);
		this.gui = gui;

	}
	
	@Override
	protected Integer doRegister(TextureResourceLocation k, boolean must) {
		AbstractTextureLoader loader = getTextureLoader(k.type());
		try {
			return loader.registerTexture(k, getUpperResourceManager());
		} catch (IOException | NullPointerException e) {
			if (must)
				throw new RuntimeException(e);
			else
				return TextureMissing.get(loader);
		}
	}
	
	@Override
	protected void close(TextureResourceLocation k, Integer v) {
		TextureLoader loader = getTextureLoader(k.type());
		loader.releaseTexture(v);
	}

	private AbstractTextureLoader getTextureLoader(TextureType k) {
		return switch (k) {
			case LEGACY -> TextureLoaderLegacy.LOADER;
			case NVG -> TextureLoaderNanoVG.getInstance(gui.getContext());
		};
	}
	
}
