package xueli.game2.resource;

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

}
