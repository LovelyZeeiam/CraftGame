package xueli.game2.renderer.legacy;

import xueli.game2.renderer.legacy.buffer.BufferStorable;

/**
 * Just as we have double buffer, we have "BackRenderBuffer" to store buffer that is generating to adapt to multithreading mesh generation
 */
public interface BackRenderBuffer {

	public void applyToBuffer(int id, BufferStorable storable);

	public void flip();

}
