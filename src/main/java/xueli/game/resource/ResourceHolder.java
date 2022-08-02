package xueli.game.resource;

public class ResourceHolder<T> {

	private ResourceManager<T> manager;
	String namespace;
	String virtualPath;

	private boolean preloaded = false;
	T result;

	ResourceHolder(ResourceManager<T> manager) {
		this.manager = manager;
	}

	ResourceHolder(ResourceManager<T> manager, String namespace, String virtualPath) {
		this.virtualPath = virtualPath;
		this.namespace = namespace;
		this.manager = manager;

	}

	// Homo specific acuteness homo特有的急性子
	public void preload() throws Exception {
		preloaded = true;
		result = manager.getLoader().load(this);
	}

	public T getResult() {
		if (!preloaded) {
			try {
				preload();
			} catch (Exception exception) {
				exception.printStackTrace();
				return null;
			}
		}
		return result;
	}

	public void release() {
	}

}
