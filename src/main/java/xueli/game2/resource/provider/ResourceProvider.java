package xueli.game2.resource.provider;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceIdentifier;

public interface ResourceProvider {

	public Resource getResource(ResourceIdentifier location) throws IOException;

	/**
	 * Find resource <b>files</b> only in the root of the pack
	 */
	public List<Resource> findResources(ResourceIdentifier location, Predicate<String> fileNamePredicate)
			throws IOException;

}
