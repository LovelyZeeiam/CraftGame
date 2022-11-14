package xueli.game2.renderer.legacy;

import xueli.game2.renderer.legacy.BackRenderBuffer;

import java.nio.ByteBuffer;

public interface RenderBuffer {

	public void applyBuffer(int id, ByteBuffer buf);

	public void setVertexCount(int count);

	public BackRenderBuffer createBackBuffer();

	public void render();
	
	public void release();

}
