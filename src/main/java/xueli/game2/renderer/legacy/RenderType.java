package xueli.game2.renderer.legacy;

import java.util.HashMap;
import java.util.function.Function;

public abstract class RenderType<T> {

	private final HashMap<T, RenderBuffer> buffers = new HashMap<>();

	private final Function<T, RenderBuffer> bufferSupplier;

	public RenderType(Function<T, RenderBuffer> bufferSupplier) {
		this.bufferSupplier = bufferSupplier;

	}

	private boolean initialized = false;

	public void init() {
		if(initialized) return;
		this.doInit();
		this.initialized = true;

	}

	protected abstract void doInit();

	public RenderBuffer getRenderBuffer(T key) {
		return buffers.computeIfAbsent(key, bufferSupplier);
	}

	public RenderBuffer releaseRenderBuffer(T key) {
		return buffers.remove(key);
	}

	public void render() {
		buffers.values().forEach(RenderBuffer::render);

	}

	public abstract void release();

}
