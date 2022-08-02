package xueli.craftgame.resource.manager;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import xueli.craftgame.resource.Resource;
import xueli.craftgame.resource.ResourceLocation;

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
