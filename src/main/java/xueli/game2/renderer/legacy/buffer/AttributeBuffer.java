package xueli.game2.renderer.legacy.buffer;

import java.util.concurrent.atomic.AtomicBoolean;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.utils.vector.Vector;

import xueli.game2.lifecycle.LifeCycle;
import xueli.game2.renderer.legacy.vertex.VertexType;

public class AttributeBuffer implements LifeCycle, Bindable {

	private final int id;
	private final int attributeSize;
	private final int bufferType = GL15.GL_DYNAMIC_DRAW;
	private final VertexType type;

	private LotsOfByteBuffer lotsOfByteBuffer;
	private int vertexCount = 0;

	private int vbo;

	public AttributeBuffer(int id, int attributeSize, VertexType type) {
		this.id = id;
		this.attributeSize = attributeSize;
		this.type = type;

		this.lotsOfByteBuffer = new LotsOfByteBuffer();

	}

	public AttributeBuffer(int id, int attributeSize, VertexType type, int initialBufferSize) {
		this.id = id;
		this.attributeSize = attributeSize;
		this.type = type;

		this.lotsOfByteBuffer = new LotsOfByteBuffer(initialBufferSize);

	}

	private boolean inited = false;

	@Override
	public void init() {
		if(inited) return;
		inited = true;

		this.vbo = GL30.glGenBuffers();
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, lotsOfByteBuffer.getBuffer(), bufferType);

		GL30.glVertexAttribPointer(id, attributeSize, type.getGlValue(), false, 0, 0);
		GL30.glEnableVertexAttribArray(id);

	}

	private AtomicBoolean shouldSyncData = new AtomicBoolean(true);

	public AttributeBuffer submit(Vector vector) {
		vector.store(lotsOfByteBuffer);
		vertexCount++;
		return this;
	}

	public void reportSyncData() {
		this.shouldSyncData.set(true);
	}

	@Override
	public void tick() {
		synchronized (this) {
			if(shouldSyncData.get()) {
				this.bind(() -> {
					lotsOfByteBuffer.setReadWrite(true);
					GL30.glBufferData(GL30.GL_ARRAY_BUFFER, lotsOfByteBuffer.getBuffer(), bufferType);
					lotsOfByteBuffer.setReadWrite(false);
				});
				shouldSyncData.set(false);
			}
		}
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void clear() {
		vertexCount = 0;
		lotsOfByteBuffer.clear();
		shouldSyncData.set(true);
	}

	@Override
	public void bind() {
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
	}

	@Override
	public void unbind() {
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
	}

	@Override
	public void release() {
		this.lotsOfByteBuffer = null;

		GL30.glDeleteBuffers(this.vbo);

	}

	public int getId() {
		return id;
	}

	public VertexType getType() {
		return type;
	}

}
