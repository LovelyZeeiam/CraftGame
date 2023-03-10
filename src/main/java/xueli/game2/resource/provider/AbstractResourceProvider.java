package xueli.game2.resource.provider;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceIdentifier;

public abstract class AbstractResourceProvider implements ResourceProvider {

	protected abstract String toVirtualPath(ResourceIdentifier location);

	@Override
	public Resource getResource(ResourceIdentifier location) throws IOException {
		String virtualPath = toVirtualPath(location);
		return this.getResource(virtualPath);
	}

	protected abstract Resource getResource(String virtualPath) throws IOException;

	@Override
	public List<Resource> findResources(ResourceIdentifier location, Predicate<String> fileNamePredicate)
			throws IOException {
		String virtualPath = toVirtualPath(location);
		return findResources(virtualPath, fileNamePredicate);
	}

	protected abstract List<Resource> findResources(String virtualPath, Predicate<String> fileNamePredicate)
			throws IOException;

}
