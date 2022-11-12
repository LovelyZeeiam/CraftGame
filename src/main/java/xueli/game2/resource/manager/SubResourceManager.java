package xueli.game2.resource.manager;

import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceLocation;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

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
	public Resource getResource(ResourceLocation location) throws IOException {
		return upperResourceManager.getResource(location);
	}

	@Override
	public List<Resource> findResources(ResourceLocation location, Predicate<String> fileNamePredicate)
			throws IOException {
		return upperResourceManager.findResources(location, fileNamePredicate);
	}
	
}
