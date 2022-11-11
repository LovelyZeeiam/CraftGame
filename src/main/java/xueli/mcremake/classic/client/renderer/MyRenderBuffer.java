package xueli.mcremake.classic.client.renderer;

import xueli.game2.renderer.legacy.RenderBuffer;
import xueli.game2.renderer.legacy.ShapeType;
import xueli.game2.renderer.legacy.VertexType;
import xueli.game2.renderer.legacy.buffer.BufferStorable;
import xueli.game2.renderer.legacy.buffer.VertexAttribute;

public class MyRenderBuffer implements RenderBuffer {

	public static final int ATTR_VERTEX = 0;
	public static final int UV_VERTEX = 1;
	public static final int COLOR_VERTEX = 2;

	private VertexAttribute attr;

	public MyRenderBuffer() {
		this.attr = new VertexAttribute(ShapeType.TRIANGLES);
		this.attr.bind(() -> {
			this.attr.addAttributeBuffer(ATTR_VERTEX, 3, VertexType.FLOAT);
			this.attr.addAttributeBuffer(UV_VERTEX, 2, VertexType.FLOAT);
			this.attr.addAttributeBuffer(COLOR_VERTEX, 3, VertexType.FLOAT);
		});

	}

	@Override
	public void reset() {

	}

	@Override
	public void sync() {

	}

	@Override
	public void render() {

	}

	@Override
	public void release() {

	}

	@Override
	public void acceptVertex(BufferStorable storable) {

	}

}
