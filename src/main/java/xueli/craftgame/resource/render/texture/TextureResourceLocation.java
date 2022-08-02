package xueli.craftgame.resource.render.texture;

import java.util.Objects;

import org.lwjgl.utils.vector.Vector2f;

import xueli.craftgame.resource.ResourceLocation;

public final class TextureResourceLocation {

	private final ResourceLocation location;
	private final TextureType type;
	private final Vector2f pLeftTop;
	private final Vector2f pRightBottom;

	private AbstractTextureLoader textureLoader;

	public TextureResourceLocation(ResourceLocation location, TextureType type, Vector2f pLeftTop,
			Vector2f pRightBottom) {
		this.location = location;
		this.type = type;
		this.pLeftTop = pLeftTop;
		this.pRightBottom = pRightBottom;

		this.textureLoader = type.getLoader();

	}

	public TextureResourceLocation(ResourceLocation location, TextureType type) {
		this(location, type, new Vector2f(0, 0), new Vector2f(1, 1));
	}

	@Override
	public String toString() {
		return "TextureResource{" + "location=" + location + ", type=" + type + ", pLeftTop=" + pLeftTop
				+ ", pRightBottom=" + pRightBottom + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TextureResourceLocation that = (TextureResourceLocation) o;
		return location.equals(that.location) && type == that.type && pLeftTop.equals(that.pLeftTop)
				&& pRightBottom.equals(that.pRightBottom);
	}

	@Override
	public int hashCode() {
		return Objects.hash(location, type, pLeftTop, pRightBottom);
	}

	public ResourceLocation location() {
		return location;
	}

	public TextureType type() {
		return type;
	}

	public Vector2f pLeftTop() {
		return pLeftTop;
	}

	public Vector2f pRightBottom() {
		return pRightBottom;
	}

	public AbstractTextureLoader getTextureLoader() {
		return textureLoader;
	}

}
