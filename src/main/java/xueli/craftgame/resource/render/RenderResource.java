package xueli.craftgame.resource.render;

import java.io.IOException;
import java.util.HashMap;

import xueli.craftgame.resource.manager.ChainedResourceManager;
import xueli.craftgame.resource.manager.SubResourceManager;

public abstract class RenderResource<K, V> extends SubResourceManager {

	private HashMap<K, V> registers = new HashMap<>();
	
	public RenderResource(ChainedResourceManager superiorManager) {
		super(superiorManager);
	}
	
	public V registerAndBind(K k, boolean must) {
		return registers.computeIfAbsent(k, Key -> this.register(k, must));
	}
	
	@Override
	public void close() throws IOException {
		super.close();
		registers.forEach(this::close);
	}
	
	protected abstract V register(K k, boolean must);
	protected abstract void close(K k, V v);
	
}
