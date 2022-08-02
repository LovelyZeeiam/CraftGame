package xueli.game.resource.texture;

import java.util.Objects;

import org.lwjgl.utils.vector.Vector2f;

import xueli.game.vector.Vector2i;

public class TextureAtlasHolder {

	private String name;
	private Vector2f offsetFrom, offsetTo;

	private boolean hasResult = false;
	private TextureAtlas atlas;
	public Vector2f p_left_top, p_left_down, p_right_top, p_right_down;
	private int atlasWidth, atlasHeight;

	public TextureAtlasHolder(String name) {
		this(name, new Vector2f(0, 0), new Vector2f(1, 1));
	}

	public TextureAtlasHolder(String name, Vector2f offsetFrom, Vector2f offsetTo) {
		this.name = name;
		this.offsetFrom = offsetFrom;
		this.offsetTo = offsetTo;

	}

	public void loadResult(TextureAtlas atlas) {
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

		this.hasResult = true;

	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	public boolean hasResult() {
		return hasResult;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TextureAtlasHolder that = (TextureAtlasHolder) o;
		return name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

}
