package xueli.game2.renderer.legacy;

import xueli.game2.renderer.legacy.buffer.BufferStorable;

/**
 * Just as we have double buffer, we have "BackRenderBuffer" to store buffer that is generating to adapt to multithreading mesh generation
 */
public interface BackRenderBuffer {

	default public void applyToBuffer(int id, BufferStorable... storable) {
		for (int i = 0; i < storable.length; i++) {
			this.applyToBuffer(id, storable[i]);
		}
	}

	public void applyToBuffer(int id, BufferStorable storable);

	public void flip();

}
