package xueli.game2.resource.submanager.render;

import xueli.game2.resource.manager.ChainedResourceManager;
import xueli.game2.resource.manager.SubResourceManager;

import java.io.IOException;
import java.util.HashMap;

public abstract class RenderResource<K, V> extends SubResourceManager {

	private HashMap<K, V> registers = new HashMap<>();
	private HashMap<K, Boolean> registerMusts = new HashMap<K, Boolean>();
	
	public RenderResource(ChainedResourceManager superiorManager) {
		super(superiorManager);
	}
	
	public V register(K k, boolean must) {
		registerMusts.put(k, must);
		V v = registers.computeIfAbsent(k, Key -> this.doRegister(k, must));
 		return v;
	}
	
	public V registerAndBind(K k, boolean must) {
		V v = registers.computeIfAbsent(k, Key -> this.doRegister(k, must));
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

		HashMap<K, V> newRegisters = new HashMap<>();
		registers.forEach((k, v) -> {
			this.close(k, v);
			V newV = this.doRegister(k, registerMusts.get(k));
			newRegisters.put(k, newV);
		});
		this.registers = newRegisters;


	}
	
	protected abstract V doRegister(K k, boolean must);
	protected abstract void bind(K k, V v);
	protected abstract void close(K k, V v);
	
}
