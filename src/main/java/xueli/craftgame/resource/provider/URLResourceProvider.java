package xueli.craftgame.resource.provider;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import xueli.craftgame.resource.Resource;
import xueli.craftgame.resource.ResourceURL;

public abstract class URLResourceProvider extends AbstractResourceProvider {

	@Override
	protected Resource getResource(String virtualPath) throws IOException {
		URL url = getResourceURL(virtualPath);
		return new ResourceURL(url);
	}

	protected abstract URL getResourceURL(String virtualPath) throws IOException;

	@Override
	protected List<Resource> findResources(String virtualPath, Predicate<String> fileNamePredicate) throws IOException {
		List<URL> resources = findResources(virtualPath);
		return resources.stream().filter(url -> fileNamePredicate.test(url.getPath())).map(ResourceURL::new)
				.collect(Collectors.toList());
	}

	protected abstract List<URL> findResources(String virtualPath) throws IOException;

}
