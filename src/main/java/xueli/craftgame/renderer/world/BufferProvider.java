package xueli.craftgame.renderer.world;

import xueli.game2.renderer.legacy.buffer.AttributeBuffer;
import xueli.game2.renderer.legacy.buffer.VertexType;
import xueli.game2.renderer.legacy.system.RenderSystem;

public class BufferProvider implements IBufferProvider {

	public static final int VERT_ATTR = 0, COLOR_ATTR = 1, UV_ATTR = 2;

	private RenderSystem system;

	private AttributeBuffer vertBuffer, colorBuffer, uvBuffer;

	public BufferProvider(RenderSystem system) {
		this.system = system;

		this.vertBuffer = system.computeIfAbsence(VERT_ATTR, key -> new AttributeBuffer(VERT_ATTR, 3, VertexType.FLOAT));
		this.colorBuffer = system.computeIfAbsence(COLOR_ATTR, key -> new AttributeBuffer(COLOR_ATTR, 3, VertexType.FLOAT));
		this.uvBuffer = system.computeIfAbsence(UV_ATTR, key -> new AttributeBuffer(UV_ATTR, 2, VertexType.FLOAT));

		system.init();

	}

	@Override
	public AttributeBuffer vertexBuffer() {
		return vertBuffer;
	}

	@Override
	public AttributeBuffer colorBuffer() {
		return colorBuffer;
	}

	@Override
	public AttributeBuffer uvBuffer() {
		return uvBuffer;
	}

}
