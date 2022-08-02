package xueli.game.resource;

import java.util.ArrayList;

import xueli.craftgame.resource.provider.ClassLoaderResourceProvider;
import xueli.craftgame.resource.provider.ResourceProvider;

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
