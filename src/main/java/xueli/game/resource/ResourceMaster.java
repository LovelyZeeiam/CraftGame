package xueli.game.resource;

import xueli.game2.resource.provider.ClassLoaderResourceProvider;
import xueli.game2.resource.provider.ResourceProvider;

import java.util.ArrayList;

public class ResourceMaster {

	private ResourceProvider provider = new ClassLoaderResourceProvider();

	private ArrayList<ResourceManager<?>> allManager = new ArrayList<>();
	private ImageResourceManager imageResourceManager;
	private TextureResourceManager textureResourceManager;

	public ResourceMaster() {
		imageResourceManager = new ImageResourceManager(this);
		textureResourceManager = new TextureResourceManager(this);

		this.allManager.add(imageResourceManager);
		this.allManager.add(textureResourceManager);

	}

	public ResourceMaster(ResourceProvider provider) {
		this.provider = provider;
	}

	public void preload() throws Exception {
		for (ResourceManager<?> manager : allManager) {
			manager.preload();
		}
	}

	public ResourceProvider getProvider() {
		return provider;
	}

	public ImageResourceManager getImageResourceManager() {
		return imageResourceManager;
	}

	public TextureResourceManager getTextureResourceManager() {
		return textureResourceManager;
	}

	public void release() {

	}

}
