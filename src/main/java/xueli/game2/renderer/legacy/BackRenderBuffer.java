package xueli.game2.renderer.legacy;

import xueli.game2.renderer.legacy.buffer.BufferStorable;

public interface BackRenderBuffer {

	default public void applyToBuffer(int id, BufferStorable... storable) {
		for (int i = 0; i < storable.length; i++) {
			this.applyToBuffer(id, storable[i]);
		}
	}

	public void applyToBuffer(int id, BufferStorable storable);

	public void flip();

}
