package xueli.game2.renderer.legacy;

import xueli.game2.renderer.legacy.buffer.LotsOfByteBuffer;

public interface RenderBuffer {

	public void applyBuffer(int id, LotsOfByteBuffer buf);

	public void setVertexCount(int count);

	public BackRenderBuffer createBackBuffer();

	public void render();
	
	public void release();

}
