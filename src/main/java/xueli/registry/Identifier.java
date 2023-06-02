package xueli.registry;

import java.util.Objects;

public record Identifier(String namespace, String location) {

	public Identifier(String location) {
		this("default", location);
	}

	public static Identifier serialize(String str) {
		str = str.trim();
		int separatorIndex = str.indexOf(':');
		if (separatorIndex < 0) {
			return new Identifier(str);
		} else {
			String namespace = str.substring(0, separatorIndex);
			String location = str.substring(separatorIndex);
			if (namespace.isBlank())
				return new Identifier(location);
			return new Identifier(namespace, location);
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
		Identifier that = (Identifier) o;
		return namespace.equals(that.namespace) && location.equals(that.location);
	}

	@Override
	public int hashCode() {
		return Objects.hash(namespace, location);
	}

}
