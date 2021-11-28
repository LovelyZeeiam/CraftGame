package xueli.game.utils.texture;

import org.lwjgl.utils.vector.Vector2f;
import xueli.game.vector.Vector2i;

public class AtlasTextureHolder {

	public Vector2f p_left_top, p_left_down, p_right_top, p_right_down;
	private TextureAtlas atlas;

	private int atlasWidth, atlasHeight;
	private String name;

	public AtlasTextureHolder(String name, TextureAtlas atlas) {
		this(name, new Vector2f(0, 0), new Vector2f(1, 1), atlas);
	}

	public AtlasTextureHolder(String name, Vector2f offsetFrom, Vector2f offsetTo, TextureAtlas atlas) {
		this.name = name;
		this.atlas = atlas;

		Vector2i index = atlas.getAtlas(name);
		if (index == null) {
			throw new RuntimeException("Found no atlas named: " + name);
		}

		this.atlasWidth = atlas.getWidth();
		this.atlasHeight = atlas.getHeight();

		p_left_top = new Vector2f((float) (index.x + offsetFrom.getX()) / atlasWidth,
				(float) (index.y + offsetFrom.getY()) / atlasHeight);
		p_left_down = new Vector2f((float) (index.x + offsetFrom.getX()) / atlasWidth,
				(float) (index.y + offsetTo.getY()) / atlasHeight);
		p_right_top = new Vector2f((float) (index.x + offsetTo.getX()) / atlasWidth,
				(float) (index.y + offsetFrom.getY()) / atlasHeight);
		p_right_down = new Vector2f((float) (index.x + offsetTo.getX()) / atlasWidth,
				(float) (index.y + offsetTo.getY()) / atlasHeight);

	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	public AtlasTextureHolder reTailor(Vector2f from, Vector2f to) {
		return new AtlasTextureHolder(this.name, from, to, this.atlas);
	}

}
