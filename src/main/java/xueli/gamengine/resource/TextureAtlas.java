package xueli.gamengine.resource;

import xueli.gamengine.utils.vector.Vector2s;

import java.util.HashMap;

public class TextureAtlas extends Texture {

	public int width = 0, height = 0;
	private HashMap<String, Vector2s> atlas = new HashMap<String, Vector2s>();

	public TextureAtlas(HashMap<String, Vector2s> atlas, int width, int height, int id, boolean isPreloading,
			boolean isGuiTexture) {
		super(id, isPreloading, isGuiTexture);
		this.atlas = atlas;
		this.width = width;
		this.height = height;

	}

	@Override
	public void bind() {
		super.bind();
	}

	@Override
	public void bind(int textureID) {
		super.bind(textureID);
	}

	public Vector2s getAtlasID(String key) {
		return atlas.get(key);
	}

}
