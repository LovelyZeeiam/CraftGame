package xueli.game2.renderer.legacy;

import xueli.game2.renderer.legacy.buffer.AttributeBuffer;
import xueli.game2.renderer.legacy.buffer.BufferStorable;
import xueli.game2.renderer.legacy.buffer.LotsOfByteBuffer;
import xueli.game2.renderer.legacy.buffer.VertexAttribute;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class VertexAttributeRenderBuffer implements RenderBuffer {

	protected final VertexAttribute attr;
	private int vertCount = 0;

	public VertexAttributeRenderBuffer(ShapeType shapeType) {
		this.attr = new VertexAttribute(shapeType);

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

//	private final BufferPool pool = new BufferPool(3, 2);

	private static class VertexBuffer {
		public int submitCount = 0;
		public LotsOfByteBuffer buf = new LotsOfByteBuffer();
	};

	@Override
	public BackRenderBuffer createBackBuffer() {
		return new BackRenderBuffer() {
			private final HashMap<Integer, VertexBuffer> applyCount = new HashMap<>() {
				{
					attr.forEachAttribute(i -> {
						VertexBuffer vertexBuffer = new VertexBuffer();
						this.put(i, vertexBuffer);
					});
				}
			};

			@Override
			public void applyToBuffer(int id, BufferStorable storable) {
				VertexBuffer buf = applyCount.get(id);
				buf.submitCount++;

				storable.store(buf.buf);

			}

			@Override
			public void flip() {
				AtomicInteger vertCount = new AtomicInteger(Integer.MAX_VALUE);
				applyCount.forEach((i, b) -> {
					vertCount.set(Math.min(vertCount.get(), b.submitCount));

					ByteBuffer buffer = b.buf.getBuffer();
					buffer.flip();
					VertexAttributeRenderBuffer.this.applyBuffer(i, buffer);

//					b.buf.release();

				});

//				System.out.println(vertCount.get());
				VertexAttributeRenderBuffer.this.setVertexCount(vertCount.get());

			}

		};
	}

	@Override
	public void render() {
//		System.out.println(vertCount);
		this.attr.render(vertCount);

	}

	@Override
	public void release() {
		this.attr.release();

	}

}
