package xueli.game.utils.texture;

import org.lwjgl.util.vector.Vector2f;

import xueli.game.vector.Vector2i;

public class AtlasTextureHolder {

	public Vector2f p_left_top, p_left_down, p_right_top, p_right_down;
	private TextureAtlas atlas;

	public AtlasTextureHolder(String name, TextureAtlas atlas) {
		this.atlas = atlas;

		Vector2i index = atlas.getAtlas(name);
		if (index == null) {
			throw new RuntimeException("Found no atlas named: " + name);
		}

		int width = atlas.getWidth();
		int height = atlas.getHeight();

		p_left_top = new Vector2f((float) index.x / width, (float) index.y / height);
		p_left_down = new Vector2f((float) index.x / width, (float) (index.y + 1) / height);
		p_right_top = new Vector2f((float) (index.x + 1) / width, (float) index.y / height);
		p_right_down = new Vector2f((float) (index.x + 1) / width, (float) (index.y + 1) / height);

	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

}
