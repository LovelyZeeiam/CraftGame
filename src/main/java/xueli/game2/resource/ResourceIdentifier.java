package xueli.game2.resource;

import java.util.Objects;

public record ResourceIdentifier(String namespace, String location) {

	public ResourceIdentifier(String location) {
		this("default", location);
	}

	public static ResourceIdentifier serialize(String str) {
		str = str.trim();
		int separatorIndex = str.indexOf(':');
		if (separatorIndex < 0) {
			return new ResourceIdentifier(str);
		} else {
			String namespace = str.substring(0, separatorIndex);
			String location = str.substring(separatorIndex);
			if (namespace.isBlank())
				return new ResourceIdentifier(location);
			return new ResourceIdentifier(namespace, location);
		}
	}

	@Override
	public String toString() {
		return namespace + ":" + location;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ResourceIdentifier that = (ResourceIdentifier) o;
		return namespace.equals(that.namespace) && location.equals(that.location);
	}

	@Override
	public int hashCode() {
		return Objects.hash(namespace, location);
	}

}
