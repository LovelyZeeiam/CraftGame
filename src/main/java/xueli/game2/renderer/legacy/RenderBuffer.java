package xueli.game2.renderer.legacy;

import xueli.game2.renderer.legacy.buffer.BufferStorable;

public interface RenderBuffer {

	public void reset();

	public void acceptVertex(BufferStorable storable);

	public void render();

}
