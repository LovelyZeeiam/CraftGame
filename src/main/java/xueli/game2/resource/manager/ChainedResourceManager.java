package xueli.game2.resource.manager;

import java.io.IOException;
import java.util.ArrayList;

import xueli.utils.logger.Logger;

public abstract class ChainedResourceManager implements ResourceManager {
	
	private static final Logger LOGGER = new Logger();
	private final ArrayList<ResourceManager> subManagers = new ArrayList<>();
	
	public ChainedResourceManager() {
	}
	
	public void addSubManager(ResourceManager manager) {
		this.subManagers.add(manager);
		LOGGER.info("Add sub-manager " + manager.getClass().getSimpleName() + " to " + this.getClass().getSimpleName());
	}
	
	@Override
	public void reload() {
		for(ResourceManager manager : subManagers) {
			manager.reload();
			LOGGER.info("Reload: " + manager.getClass().getSimpleName());
		}

	}
	
	@Override
	public void close() throws IOException {
		for(ResourceManager manager : subManagers) {
			manager.close();
			LOGGER.info("Close: " + manager.getClass().getSimpleName());
		}
	}

}
