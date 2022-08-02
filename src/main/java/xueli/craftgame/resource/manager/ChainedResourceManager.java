package xueli.craftgame.resource.manager;

import java.io.IOException;
import java.util.ArrayList;

import xueli.utils.logger.MyLogger;

public abstract class ChainedResourceManager implements ResourceManager {
	
	private static final MyLogger LOGGER = new MyLogger() {
		{
			pushState("ResourceManager");
		}
	};
	
	private ArrayList<ResourceManager> subResourceManagers = new ArrayList<>();
	
	public ChainedResourceManager() {
	}
	
	public void addSubManager(ResourceManager manager) {
		this.subResourceManagers.add(manager);
		LOGGER.info("Add Sub " + manager.getClass().getName() + " to " + this.getClass().getName());
	}
	
	@Override
	public void reload() {
		for(ResourceManager subManager : subResourceManagers) {
			subManager.reload();
			LOGGER.info("Reload: " + subManager.getClass().getName());
		}
	}
	
	public void removeSubManager(ResourceManager manager) {
		this.subResourceManagers.remove(manager);
	}
	
	@Override
	public void close() throws IOException {
		for(ResourceManager manager : subResourceManagers) {
			manager.close();
			LOGGER.info("Close: " + manager.getClass().getName());
		}
	}

}
