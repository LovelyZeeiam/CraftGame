package xueli.game2.renderer.legacy.buffer;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import xueli.game2.lifecycle.LifeCycle;
import xueli.game2.renderer.legacy.VertexType;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

public class AttributeBuffer implements LifeCycle, Bindable {

	private final int id;
	private final int attributeSize;
	private final int bufferType = GL15.GL_DYNAMIC_DRAW;
	private final VertexType type;

	private int vbo;

	public AttributeBuffer(int id, int attributeSize, VertexType type) {
		this.id = id;
		this.attributeSize = attributeSize;
		this.type = type;

	}

	@Override
	public void init() {
		this.vbo = GL30.glGenBuffers();
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, 0, bufferType);

		GL30.glVertexAttribPointer(id, attributeSize, type.getGlValue(), false, 0, 0);
		GL30.glEnableVertexAttribArray(id);

		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);

	}

	private final AtomicBoolean shouldSyncData = new AtomicBoolean(true);
	private ByteBuffer toBeSyncData;

	@Override
	public void tick() {
		synchronized (this) {
			if(shouldSyncData.get()) {
				this.bind(() -> GL30.glBufferData(GL30.GL_ARRAY_BUFFER, toBeSyncData, bufferType));
				shouldSyncData.set(false);
			}
		}
	}

	public void updateBuffer(ByteBuffer buffer) {
		this.toBeSyncData = buffer;
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
