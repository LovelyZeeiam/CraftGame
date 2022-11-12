package xueli.game2.resource.manager;

import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.provider.ResourceProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class BackwardResourceManager extends ChainedResourceManager {

	private List<ResourceProvider> providers;

	public BackwardResourceManager(List<ResourceProvider> providers) {
		this.providers = Objects.requireNonNull(providers);
		reload();
	}

	public List<ResourceProvider> getProviders() {
		return List.copyOf(this.providers);
	}

	public void setProviders(List<ResourceProvider> providers) {
		this.providers = Objects.requireNonNull(providers);
		reload();
	}

	@Override
	public Resource getResource(ResourceLocation location) throws IOException {
		for (ResourceProvider provider : providers) {
			try {
				return provider.getResource(location);
			} catch (IOException e) {
			}
		}
		return null;
	}

	@Override
	public List<Resource> findResources(ResourceLocation location, Predicate<String> fileNamePredicate)
			throws IOException {
		ArrayList<Resource> resources = new ArrayList<>();
		for (ResourceProvider provider : providers) {
			try {
				resources.addAll(provider.findResources(location, fileNamePredicate));
			} catch (IOException e) {
			}
		}
		return resources;
	}

}
