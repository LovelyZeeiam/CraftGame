package xueli.game2.resource.submanager.render.texture;

import java.util.Objects;

import xueli.game2.resource.ResourceLocation;

public final class TextureResourceLocation {

	private final ResourceLocation location;
	private final TextureType type;

	private AbstractTextureLoader textureLoader;

	public TextureResourceLocation(ResourceLocation location, TextureType type) {
		this.location = location;
		this.type = type;

		this.textureLoader = type.getLoader();

	}

	@Override
	public String toString() {
		return "TextureResourceLocation{" +
				"location=" + location +
				", type=" + type +
				", textureLoader=" + textureLoader +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TextureResourceLocation that = (TextureResourceLocation) o;
		return location.equals(that.location) && type == that.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(location, type);
	}

	public ResourceLocation location() {
		return location;
	}

	public TextureType type() {
		return type;
	}

	public AbstractTextureLoader getTextureLoader() {
		return textureLoader;
	}

}
