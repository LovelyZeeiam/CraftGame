package xueli.game.resource;

import xueli.game.resource.texture.TextureAtlas;
import xueli.game.resource.texture.TextureAtlasBuilder;
import xueli.game.resource.texture.TextureAtlasHolder;

import java.util.HashMap;

class TextureAtlasManager extends ResourceManager<TextureAtlas> {

	private final HashMap<String, TextureAtlasBuilder> builders = new HashMap<>();
	private final HashMap<String, TextureAtlas> atlases = new HashMap<>();

	private HashMap<String, TextureAtlasHolder> namespaceToHolder = new HashMap<>();

	TextureAtlasManager(ResourceMaster master) {
		super(master);

	}

	@Override
	public ResourceHolder<TextureAtlas> addToken(String namespace, String virtualPath) {
		throw new UnsupportedOperationException("");
	}

	public void addBuiltList(String namespace, TextureAtlasBuilder builder) {
		builders.put(namespace, builder);
		// System.out.println(namespace);

		builder.getTextureMaps().keySet().forEach(n -> {
			TextureAtlasHolder holder = new TextureAtlasHolder(n);
			namespaceToHolder.put(n, holder);
		});

	}

	@Override
	public void preload() throws Exception {
		// System.out.println(builders.size());
		builders.forEach((n, builder) -> {
			TextureAtlas atlas = TextureAtlas.generateAtlas(builder);
			atlases.put(n, atlas);
			// System.out.println(atlas + ", " + builder.getTextureMaps().keySet().size());
			builder.getTextureMaps().keySet().forEach(namespace -> {
				TextureAtlasHolder holder = namespaceToHolder.get(namespace);
				holder.loadResult(atlas);
			});
		});
	}

	public TextureAtlasHolder getHolder(String namespace) {
		return namespaceToHolder.get(namespace);
	}

	public TextureAtlas getAtlas(String namespace) {
		return atlases.get(namespace);
	}

}
