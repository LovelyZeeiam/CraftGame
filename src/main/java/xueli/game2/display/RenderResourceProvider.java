package xueli.game2.display;

import xueli.game2.resource.manager.ResourceManager;

// TODO: make GameDisplay extend this
public interface RenderResourceProvider {

	public <T extends ResourceManager> T getResourceManager(Class<T> clazz);

}
