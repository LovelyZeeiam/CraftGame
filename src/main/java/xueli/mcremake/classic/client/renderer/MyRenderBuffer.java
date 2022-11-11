package xueli.mcremake.classic.client.renderer;

import xueli.game2.renderer.legacy.RenderBuffer;
import xueli.game2.renderer.legacy.ShapeType;
import xueli.game2.renderer.legacy.VertexType;
import xueli.game2.renderer.legacy.buffer.AttributeBuffer;
import xueli.game2.renderer.legacy.buffer.VertexAttribute;

import java.nio.ByteBuffer;

public class MyRenderBuffer implements RenderBuffer {

	public static final int ATTR_VERTEX = 0;
	public static final int UV_VERTEX = 1;
	public static final int COLOR_VERTEX = 2;

	private final VertexAttribute attr;
	private int vertCount = 0;

	public MyRenderBuffer() {
		this.attr = new VertexAttribute(ShapeType.TRIANGLES);
		this.attr.bind(() -> {
			this.attr.addAttributeBuffer(ATTR_VERTEX, 3, VertexType.FLOAT);
			this.attr.addAttributeBuffer(UV_VERTEX, 2, VertexType.FLOAT);
			this.attr.addAttributeBuffer(COLOR_VERTEX, 3, VertexType.FLOAT);
		});

	}

	@Override
	public void applyBuffer(int id, ByteBuffer buf) {
		AttributeBuffer atb = this.attr.getAttributeBuffer(id);
		atb.updateBuffer(buf);

	}

	@Override
	public void setVertexCount(int count) {
		this.vertCount = count;
	}

	@Override
	public void render() {
		this.attr.render(vertCount);

	}

	@Override
	public void release() {
		this.attr.release();

	}

}
