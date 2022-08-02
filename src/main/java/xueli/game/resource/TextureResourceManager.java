package xueli.game.resource;

import xueli.game.resource.texture.TextureAtlas;
import xueli.game.resource.texture.TextureAtlasBuilder;
import xueli.game.resource.texture.TextureAtlasHolder;

public class TextureResourceManager extends ResourceManager<TextureAtlasHolder>
		implements ResourceLoader<TextureAtlasHolder>, MissingProvider<TextureAtlasHolder> {

	private TextureAtlasManager atlasManager;
	private boolean textureAtlasPreloaded = false;

	TextureResourceManager(ResourceMaster master) {
		super(master);
		setLoader(this);
		setMissingProvider(this);

		atlasManager = new TextureAtlasManager(master);

	}

	@Override
	public ResourceHolder<TextureAtlasHolder> addToken(String namespace, String virtualPath) {
		throw new UnsupportedOperationException("");
	}

	public TextureAtlasHolder addToken(String namespace) {
		TextureAtlasHolder holder = atlasManager.getHolder(namespace);
		holderMap.put(namespace, new ResourceHolder<>(null, namespace, null));
		if (holder == null) {
			System.err.println("Can't find holder: " + namespace);
		}
		return holder;
	}

	public void build(String namespace, TextureAtlasBuilder builder) {
		atlasManager.addBuiltList(namespace, builder);
	}

	@Override
	public TextureAtlasHolder load(ResourceHolder<TextureAtlasHolder> holder) throws Exception {
		checkAtlasManagerPreloaded();
		return atlasManager.getHolder(holder.namespace);
	}

	@Override
	public void preload() throws Exception {
		checkAtlasManagerPreloaded();
	}

	private void checkAtlasManagerPreloaded() throws Exception {
		if (textureAtlasPreloaded)
			return;
		this.atlasManager.preload();
		// System.out.println("checked");
		textureAtlasPreloaded = true;
	}

	@Override
	public void onMissing(ResourceHolder<TextureAtlasHolder> holder) {
		holder.result = TextureMissing.getMissingTexture();
	}

	public TextureAtlas getAtlas(String namespace) {
		return atlasManager.getAtlas(namespace);
	}

}
