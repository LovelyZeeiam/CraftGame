package xueLi.gamengine.resource;

import java.util.HashMap;

public class TextureAtlas extends Texture {

	private HashMap<String, Integer> atlas = new HashMap<String, Integer>();

	public TextureAtlas(HashMap<String, Integer> atlas, int id, boolean isPreloading, boolean isGuiTexture) {
		super(id, isPreloading, isGuiTexture);
		this.atlas = atlas;

	}

	public Integer getAtlasID(String key) {
		return atlas.get(key);
	}

}
