package xueli.mcremake.classic.client.renderer;

import xueli.game2.renderer.legacy.BackRenderBuffer;
import xueli.game2.renderer.legacy.RenderBuffer;
import xueli.game2.renderer.legacy.ShapeType;
import xueli.game2.renderer.legacy.VertexType;
import xueli.game2.renderer.legacy.buffer.AttributeBuffer;
import xueli.game2.renderer.legacy.buffer.BufferStorable;
import xueli.game2.renderer.legacy.buffer.LotsOfByteBuffer;
import xueli.game2.renderer.legacy.buffer.VertexAttribute;

import java.nio.ByteBuffer;
import java.util.HashMap;

public class MyRenderBuffer implements RenderBuffer {

	public static final int ATTR_VERTEX = 0;
	public static final int UV_VERTEX = 1;
	public static final int COLOR_VERTEX = 2;
	private static final int[] ATTRS = { ATTR_VERTEX, UV_VERTEX, COLOR_VERTEX };

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

	private class Buffer {
		public int vertCount = 0;
		public LotsOfByteBuffer buf = new LotsOfByteBuffer();
	};

	@Override
	public BackRenderBuffer createBackBuffer() {
		return new BackRenderBuffer() {
			private final HashMap<Integer, Buffer> applyCount = new HashMap<>() {
				{
					for (int i = 0; i < ATTRS.length; i++) {
						applyCount.put(ATTRS[i], new Buffer());
					}
				}
			};

			@Override
			public void applyToBuffer(int id, BufferStorable storable) {
				Buffer buf = applyCount.get(id);
				buf.vertCount++;

				storable.store(buf.buf);

			}

			@Override
			public void flip() {
				int vertCount = 0;
				for (int i = 0; i < ATTRS.length; i++) {
					Buffer buffer = applyCount.get(ATTRS[i]);

					vertCount = Math.min(vertCount, buffer.vertCount);
					MyRenderBuffer.this.applyBuffer(ATTRS[i], buffer.buf.getBuffer());

				}

				MyRenderBuffer.this.setVertexCount(vertCount);

			}
		};
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
