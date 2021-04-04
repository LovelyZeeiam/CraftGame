package xueli.game.utils;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import xueli.game.vector.Vector2s;

public class TextureAtlas {

	public int width = 0, height = 0;
	private int id;
	private HashMap<String, Vector2s> atlas = new HashMap<String, Vector2s>();

	public TextureAtlas(HashMap<String, Vector2s> atlas, int width, int height, int id) {
		this.id = id;
		this.atlas = atlas;
		this.width = width;
		this.height = height;

	}

	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

	public Vector2s getAtlas(String key) {
		return atlas.get(key);
	}

}
