package xueli.craftgame.renderer.blocks.buffer;

import xueli.game2.renderer.legacy.buffer.AttributeBuffer;

public interface IBufferProvider {

	public AttributeBuffer vertexBuffer();

	public AttributeBuffer colorBuffer();

	public AttributeBuffer uvBuffer();

}
