package xueli.game2.resource.provider;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import xueli.game2.resource.Resource;
import xueli.registry.Identifier;

public abstract class AbstractResourceProvider implements ResourceProvider {

	protected abstract String toVirtualPath(Identifier location);

	@Override
	public Resource getResource(Identifier location) throws IOException {
		String virtualPath = toVirtualPath(location);
		return this.getResource(virtualPath);
	}

	protected abstract Resource getResource(String virtualPath) throws IOException;

	@Override
	public List<Resource> findResources(Identifier location, Predicate<String> fileNamePredicate)
			throws IOException {
		String virtualPath = toVirtualPath(location);
		return findResources(virtualPath, fileNamePredicate);
	}

	protected abstract List<Resource> findResources(String virtualPath, Predicate<String> fileNamePredicate)
			throws IOException;

}
