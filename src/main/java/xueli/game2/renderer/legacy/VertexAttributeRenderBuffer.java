package xueli.game2.renderer.legacy;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import xueli.game2.renderer.legacy.buffer.AttributeBuffer;
import xueli.game2.renderer.legacy.buffer.BufferStorable;
import xueli.game2.renderer.legacy.buffer.LotsOfByteBuffer;
import xueli.game2.renderer.legacy.buffer.VertexAttribute;

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

//	private final BufferPool pool = new BufferPool(3, 2);

	private static class VertexBuffer {
		public int submitCount = 0;
		public LotsOfByteBuffer buf = new LotsOfByteBuffer();
	};

	@Override
	public BackRenderBuffer createBackBuffer() {
		return new BackRenderBuffer() {
			private final HashMap<Integer, VertexBuffer> applyCount = new HashMap<>() {
				private static final long serialVersionUID = 5062673955963603115L;

				{
					attr.forEachAttribute(i -> {
						VertexBuffer vertexBuffer = new VertexBuffer();
						this.put(i, vertexBuffer);
					});
				}
			};

			@Override
			public void applyToBuffer(int id, BufferStorable storable) {
				VertexBuffer obj = applyCount.get(id);
				storable.store(obj.buf);
				obj.submitCount++;

			}

			@Override
			public void flip() {
				// TODO: This time we manage the memory by LWJGL API
				// Actually just now I practise C# and use Memory handle which works pretty nice
				// Or it might appear some unexpected behaviors
				AtomicInteger vertCount = new AtomicInteger(Integer.MAX_VALUE);
				applyCount.forEach((i, b) -> {
					vertCount.set(Math.min(vertCount.get(), b.submitCount));
					
					b.buf.setReadWrite(true);
					VertexAttributeRenderBuffer.this.applyBuffer(i, b.buf);
					
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
