package xueli.game2.renderer.legacy;

import xueli.game2.renderer.legacy.buffer.BufferStorable;

public interface RenderBuffer extends VertexAcceptable {

	public void reset();

	default public void acceptVertex(BufferStorable... storables) {
		for (BufferStorable storable : storables) {
			this.acceptVertex(storable);
		}
	}

	public void render();

	public void release();

}
