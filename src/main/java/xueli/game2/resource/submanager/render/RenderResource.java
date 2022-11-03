package xueli.game2.resource.submanager.render;

import xueli.game2.resource.manager.ChainedResourceManager;
import xueli.game2.resource.manager.SubResourceManager;

import java.io.IOException;
import java.util.HashMap;

public abstract class RenderResource<K, V> extends SubResourceManager {

	private HashMap<K, V> registers = new HashMap<>();
	
	public RenderResource(ChainedResourceManager superiorManager) {
		super(superiorManager);
	}
	
	public V preRegister(K k, boolean must) {
		V v = registers.computeIfAbsent(k, Key -> this.register(k, must));
 		return v;
	}
	
	public V registerAndBind(K k, boolean must) {
		V v = registers.computeIfAbsent(k, Key -> this.register(k, must));
		this.bind(k, v);
 		return v;
	}
	
	@Override
	public void close() throws IOException {
		super.close();
		registers.forEach(this::close);
	}
	
	@Override
	public void reload() {
		super.reload();
		registers.clear();
	}
	
	protected abstract V register(K k, boolean must);
	protected abstract void bind(K k, V v);
	protected abstract void close(K k, V v);
	
}
