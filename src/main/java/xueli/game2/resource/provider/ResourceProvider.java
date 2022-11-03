package xueli.game2.resource.provider;

import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceLocation;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public interface ResourceProvider {

	public Resource getResource(ResourceLocation location) throws IOException;

	/**
	 * Find resource <b>files</b> only in the root of the pack
	 */
	public List<Resource> findResources(ResourceLocation location, Predicate<String> fileNamePredicate)
			throws IOException;

}
