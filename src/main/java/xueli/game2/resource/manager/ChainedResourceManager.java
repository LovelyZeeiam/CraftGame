package xueli.game2.resource.manager;

import xueli.game2.resource.ResourceHolder;
import xueli.utils.logger.MyLogger;

import java.io.IOException;
import java.util.ArrayList;

public abstract class ChainedResourceManager implements ResourceManager {
	
	private static final MyLogger LOGGER = new MyLogger() {
		{
			pushState("ResourceManager");
		}
	};
	
	private final ArrayList<ResourceHolder> resourceHolders = new ArrayList<>();
	private final ArrayList<ResourceManager> subManagers = new ArrayList<>();
	
	public ChainedResourceManager() {
	}

	public void addSubManager(ResourceManager manager) {
		this.subManagers.add(manager);
		LOGGER.info("Add sub-manager " + manager.getClass().getName() + " to " + this.getClass().getName());
	}

	public void addResourceHolder(ResourceHolder holder) {
		holder.reload();
		this.resourceHolders.add(holder);
		LOGGER.info("Add holder " + holder.getClass().getName() + " to " + this.getClass().getName());
	}
	
	@Override
	public void reload() {
		for(ResourceManager manager : subManagers) {
			manager.reload();
			LOGGER.info("Reload: " + manager.getClass().getName());
		}
		for (ResourceHolder holder : resourceHolders) {
			holder.reload();
			LOGGER.info("Holder reload: " + holder.getClass().getName());
		}

	}
	
	@Override
	public void close() throws IOException {
		for(ResourceManager manager : subManagers) {
			manager.close();
			LOGGER.info("Close: " + manager.getClass().getName());
		}
	}

}
