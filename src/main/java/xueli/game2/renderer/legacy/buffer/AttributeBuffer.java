package xueli.game2.renderer.legacy.buffer;

import java.util.concurrent.atomic.AtomicBoolean;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import xueli.game2.renderer.legacy.VertexType;

public class AttributeBuffer implements Bindable {

	private final int id;
	private final int attributeSize;
	private final int bufferType = GL15.GL_DYNAMIC_DRAW;
	private final VertexType type;

	private int vbo;

	private BufferSyncor bufferManager = new BufferSyncor();

	public AttributeBuffer(int id, int attributeSize, VertexType type) {
		this.id = id;
		this.attributeSize = attributeSize;
		this.type = type;

	}

	public void init() {
		this.vbo = GL30.glGenBuffers();
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, 0, bufferType);

		GL30.glVertexAttribPointer(id, attributeSize, type.getGlValue(), false, 0, 0);
		GL30.glEnableVertexAttribArray(id);

		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);

	}

	public void tick() {
		synchronized (this) {
			bufferManager.doingSyncIfNecessary(buf -> {
				this.bind(() -> GL30.glBufferData(GL30.GL_ARRAY_BUFFER, buf.getBuffer(), bufferType));
			});
		}
	}

	public void updateBuffer(LotsOfByteBuffer buf) {
		bufferManager.updateBuffer(buf);
    }

	public BufferSyncor.BackBuffer createBackBuffer() {
		return bufferManager.createBackBuffer();
	}

	@Override
	public void bind() {
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
	}

	@Override
	public void unbind() {
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
	}

	public void release() {
		GL30.glDeleteBuffers(this.vbo);

	}

	public int getId() {
		return id;
	}

	public int getAttributeSize() {
		return attributeSize;
	}

	public VertexType getType() {
		return type;
	}

}
