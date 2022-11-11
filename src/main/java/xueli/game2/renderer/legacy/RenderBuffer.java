package xueli.game2.renderer.legacy;

import java.nio.ByteBuffer;

public interface RenderBuffer {

	public void applyBuffer(int id, ByteBuffer buf);

	public void setVertexCount(int count);

	public void render();
	
	public void release();

}
