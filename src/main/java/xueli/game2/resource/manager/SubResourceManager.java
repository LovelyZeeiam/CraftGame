package xueli.game2.resource.manager;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import xueli.game2.resource.Resource;
import xueli.registry.Identifier;

public class SubResourceManager extends ChainedResourceManager {

	private ChainedResourceManager upperResourceManager;

	public SubResourceManager(ChainedResourceManager superiorManager) {
		this.upperResourceManager = superiorManager;
		superiorManager.addSubManager(this);
	}

	public ChainedResourceManager getUpperResourceManager() {
		return upperResourceManager;
	}

	@Override
	public Resource getResource(Identifier location) throws IOException {
		return upperResourceManager.getResource(location);
	}

	@Override
	public List<Resource> findResources(Identifier location, Predicate<String> fileNamePredicate)
			throws IOException {
		return upperResourceManager.findResources(location, fileNamePredicate);
	}

}
