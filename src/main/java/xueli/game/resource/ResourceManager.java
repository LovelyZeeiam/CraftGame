package xueli.game.resource;

import java.util.HashMap;

public class ResourceManager<T> {

	protected ResourceMaster master;
	protected HashMap<String, ResourceHolder<T>> holderMap = new HashMap<>();
	protected ResourceLoader<T> loader;

	private MissingProvider<T> missingProvider = new MissingProvider<T>() {
		@Override
		public void onMissing(ResourceHolder<T> holder) {
			holder.result = null;
		}
	};

	public ResourceManager(ResourceLoader<T> loader, ResourceMaster master) {
		this.loader = loader;
		this.master = master;

	}

	ResourceManager(ResourceMaster master) {
		this.master = master;

	}

	public ResourceHolder<T> addToken(String namespace, String virtualPath) {
		ResourceHolder<T> holder = holderMap.get(namespace);
		if (holder == null) {
			holder = new ResourceHolder<T>(this, namespace, virtualPath);
			holderMap.put(namespace, holder);
		}
		return holder;
	}

	public void removeToken(String namespace) {
		holderMap.remove(namespace);
	}

	public void preload() throws Exception {
		for (ResourceHolder<T> value : holderMap.values()) {
			value.preload();
		}
	}

	public ResourceLoader<T> getLoader() {
		return loader;
	}

	public ResourceManager<T> setLoader(ResourceLoader<T> loader) {
		this.loader = loader;
		return this;
	}

	public MissingProvider<T> getMissingProvider() {
		return missingProvider;
	}

	public ResourceManager<T> setMissingProvider(MissingProvider<T> missingProvider) {
		this.missingProvider = missingProvider;
		return this;
	}

}
