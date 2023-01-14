package xueli.game2.renderer.legacy;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import org.lwjgl.utils.vector.Matrix4f;

public abstract class RenderType<T> {

	protected final HashMap<T, RenderBuffer> buffers = new HashMap<>();

	private final Function<T, RenderBuffer> bufferSupplier;

	public RenderType(Function<T, RenderBuffer> bufferSupplier) {
		this.bufferSupplier = bufferSupplier;

	}

	public RenderBuffer getRenderBuffer(T key) {
		return buffers.computeIfAbsent(key, bufferSupplier);
	}

	public RenderBuffer releaseRenderBuffer(T key) {
		RenderBuffer buffer = buffers.remove(key);
		buffer.release();
		return buffer;
	}

	public void render() {
		buffers.values().forEach(RenderBuffer::render);
	}
	
	public void render(Predicate<T> selector) {
		buffers.forEach((t, b) -> {
			if(selector.test(t)) {
				b.render();
			}
		});;
	}

	public abstract void applyMatrix(String name, Matrix4f matrix);
	
	public void clear() {
		buffers.values().forEach(RenderBuffer::clear);
	}
	
	public void release() {
		buffers.values().forEach(RenderBuffer::release);
		this.doRelease();
	}

	protected abstract void doRelease();

}
