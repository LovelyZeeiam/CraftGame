package xueli.game2.resource.submanager.render.texture;

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
