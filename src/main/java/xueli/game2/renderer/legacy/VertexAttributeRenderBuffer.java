package xueli.game2.renderer.legacy;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import xueli.game2.renderer.legacy.buffer.AttributeBuffer;
import xueli.game2.renderer.legacy.buffer.BufferStorable;
import xueli.game2.renderer.legacy.buffer.BufferSyncor;
import xueli.game2.renderer.legacy.buffer.LotsOfByteBuffer;
import xueli.game2.renderer.legacy.buffer.VertexAttribute;
import xueli.game2.renderer.legacy.shape.ShapeType;

public class VertexAttributeRenderBuffer implements RenderBuffer {

	protected final VertexAttribute attr;
	private int vertCount = 0;

	public VertexAttributeRenderBuffer(ShapeType shapeType) {
		this.attr = new VertexAttribute(shapeType);

	}

	@Override
	public void applyBuffer(int id, LotsOfByteBuffer buf) {
		AttributeBuffer atb = this.attr.getAttributeBuffer(id);
		atb.updateBuffer(buf);

	}

	@Override
	public void setVertexCount(int count) {
		this.vertCount = count;
	}
	
	@Override
	public void clear() {
		this.vertCount = 0;
	}

	protected static class VertexBuffer {
        public int submitCount = 0;
		public BufferSyncor.BackBuffer buf;

		public VertexBuffer(BufferSyncor.BackBuffer buf) {
			this.buf = buf;
        }

	};

	@Override
	public BackRenderBuffer createBackBuffer() {
		return new BackRenderBuffer() {
			private final HashMap<Integer, VertexBuffer> applyCount = new HashMap<>() {
				private static final long serialVersionUID = 5062673955963603115L;

				{
					attr.forEachAttribute(i -> {
						AttributeBuffer buf = attr.getAttributeBuffer(i);
						VertexBuffer vertexBuffer = new VertexBuffer(buf.createBackBuffer());
						this.put(i, vertexBuffer);
					});
				}
			};

			@Override
			public void applyToBuffer(int id, BufferStorable storable) {
				VertexBuffer obj = applyCount.get(id);
				storable.store(obj.buf.getBuffer());
				obj.submitCount++;

			}

			@Override
			public void flip() {
				AtomicInteger vertCount = new AtomicInteger(Integer.MAX_VALUE);
				applyCount.forEach((i, b) -> {
					vertCount.set(Math.min(vertCount.get(), b.submitCount));
					b.buf.markSync();
				});

				VertexAttributeRenderBuffer.this.setVertexCount(vertCount.get());

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

	protected int getVertCount() {
		return vertCount;
	}

}
