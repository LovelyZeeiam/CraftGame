package xueli.game2.resource.submanager.render.texture;

import xueli.game2.resource.ResourceLocation;

import java.util.Objects;

public record TextureResourceLocation(ResourceLocation location, TextureLoaderBuilder type) {

	@Override
	public String toString() {
		return "TextureResourceLocation{" +
				"location=" + location +
				", type=" + type +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TextureResourceLocation that = (TextureResourceLocation) o;
		return location.equals(that.location) && type.equals(that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(location, type);
	}

}
