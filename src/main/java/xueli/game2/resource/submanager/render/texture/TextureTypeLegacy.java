package xueli.game2.resource.submanager.render.texture;

@Deprecated
public class TextureTypeLegacy implements TextureLoaderBuilder {

	@Override
	public AbstractTextureLoader getLoader(TextureRenderResource resManager) {
		return new TextureLoaderLegacy();
	}

}
