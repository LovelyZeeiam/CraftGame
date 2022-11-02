package xueli.game2.resource;

import java.util.Objects;

public record ResourceLocation(String namespace, String location) {

	public ResourceLocation(String location) {
		this("default", location);
	}

	public static ResourceLocation serialize(String str) {
		str = str.trim();
		int separatorIndex = str.indexOf(':');
		if (separatorIndex < 0) {
			return new ResourceLocation(str);
		} else {
			String namespace = str.substring(0, separatorIndex);
			String location = str.substring(separatorIndex);
			if (namespace.isBlank())
				return new ResourceLocation(location);
			return new ResourceLocation(namespace, location);
		}
	}

	@Override
	public String toString() {
		return namespace + ":" + location;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ResourceLocation that = (ResourceLocation) o;
		return namespace.equals(that.namespace) && location.equals(that.location);
	}

	@Override
	public int hashCode() {
		return Objects.hash(namespace, location);
	}

}
