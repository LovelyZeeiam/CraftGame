package xueli.game2.resource.submanager.render;

import java.io.IOException;
import java.util.HashMap;

import xueli.game2.resource.ReloadableResourceTicket;
import xueli.game2.resource.manager.ChainedResourceManager;
import xueli.game2.resource.manager.SubResourceManager;
import xueli.utils.logger.Logger;

public abstract class RenderResource<K, V> extends SubResourceManager {

	public static final Logger LOGGER = new Logger();

	private HashMap<K, V> registers = new HashMap<>();
	private final HashMap<K, Boolean> registerMusts = new HashMap<>();

	public RenderResource(ChainedResourceManager superiorManager) {
		super(superiorManager);
	}

	public ReloadableResourceTicket<V> register(K k, boolean must) {
		registerMusts.put(k, must);
		return () -> registers.computeIfAbsent(k, k1 -> {
			V v1 = this.doRegister(k1, must);
			LOGGER.info("Register \"" + k + "\" in " + getClass().getSimpleName());
			return v1;
		});
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

	protected abstract void close(K k, V v);

}
