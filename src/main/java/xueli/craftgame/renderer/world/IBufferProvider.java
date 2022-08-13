package xueli.craftgame.renderer.world;

import xueli.game2.renderer.legacy.buffer.AttributeBuffer;

public interface IBufferProvider {

	public AttributeBuffer vertexBuffer();

	public AttributeBuffer colorBuffer();

	public AttributeBuffer uvBuffer();

}
