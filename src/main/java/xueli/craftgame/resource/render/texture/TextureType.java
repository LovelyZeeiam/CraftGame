package xueli.craftgame.resource.render.texture;

public enum TextureType {

	LEGACY(new TextureLoaderLegacy()), NVG(new TextureLoaderNanoVG());

	private AbstractTextureLoader manager;

	private TextureType(AbstractTextureLoader manager) {
		this.manager = manager;
	}

	public AbstractTextureLoader getLoader() {
		return manager;
	}

}
