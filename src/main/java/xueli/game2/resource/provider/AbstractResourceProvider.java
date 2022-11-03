package xueli.game2.resource.provider;

import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceLocation;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractResourceProvider implements ResourceProvider {

	protected abstract String toVirtualPath(ResourceLocation location);

	@Override
	public Resource getResource(ResourceLocation location) throws IOException {
		String virtualPath = toVirtualPath(location);
		return this.getResource(virtualPath);
	}

	protected abstract Resource getResource(String virtualPath) throws IOException;

	@Override
	public List<Resource> findResources(ResourceLocation location, Predicate<String> fileNamePredicate)
			throws IOException {
		String virtualPath = toVirtualPath(location);
		return findResources(virtualPath, fileNamePredicate);
	}

	protected abstract List<Resource> findResources(String virtualPath, Predicate<String> fileNamePredicate)
			throws IOException;

}
