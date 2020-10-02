package xueLi.gamengine.resource;

import java.util.HashMap;

import xueLi.gamengine.utils.vector.Vector2s;

public class TextureAtlas extends Texture {

	private HashMap<String, Vector2s> atlas = new HashMap<String, Vector2s>();

	public int width = 0, height = 0;

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

	public Vector2s getAtlasID(String key) {
		return atlas.get(key);
	}

}
