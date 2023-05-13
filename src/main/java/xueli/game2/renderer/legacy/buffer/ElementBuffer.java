package xueli.game2.renderer.legacy.buffer;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class ElementBuffer implements Bindable {
    
    private int ebo;
    private final int bufferType = GL15.GL_DYNAMIC_DRAW;

    private BufferSyncor bufferManager = new BufferSyncor();

    public ElementBuffer() {
        this.ebo = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, this.ebo);
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, 0, bufferType);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);

    }

    public void tick() {
        synchronized(this) {
            bufferManager.doingSyncIfNecessary(buf -> {
				this.bind(() -> GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, buf.getBuffer(), bufferType));
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
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, this.ebo);
    }

    @Override
    public void unbind() {
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void release() {
        GL30.glDeleteBuffers(this.ebo);
    }

    public int getId() {
        return ebo;
    }

}
