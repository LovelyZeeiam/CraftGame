package xueli.utils.buffer;

import java.util.function.Consumer;

// Will the memory be freed by OpenGL or OpenAL instead of having to be freed by ourselves?   —— LovelyZeeiam
/*
 * This object acts as a buffer manager, providing back buffer or take control of an external buffer.
 * So buffers will be automatically freed when it is appropriate time. 
 */
public class BufferSyncor {

	LotsOfByteBuffer currentBuffer;
	private boolean shouldSync = false;

	private LotsOfByteBuffer toBeSyncBuffer;

	public BufferSyncor() {
	}

	public synchronized BackBuffer createBackBuffer() {
		if (toBeSyncBuffer != null) {
			toBeSyncBuffer.release();
		}
		toBeSyncBuffer = new LotsOfByteBuffer();
		toBeSyncBuffer.setReadWrite(false);
		return new BackBuffer() {

			@Override
			public LotsOfByteBuffer getBuffer() {
				return toBeSyncBuffer;
			}

			@Override
			public void markSync() {
				toBeSyncBuffer.setReadWrite(true);
				shouldSync = true;
			}

		};
	}

//	public LotsOfByteBuffer getLatestBuffer() {
//		return toBeSyncBuffer == null ? currentBuffer : toBeSyncBuffer;
//	}

	public synchronized void updateBuffer(LotsOfByteBuffer buf) {
		if (toBeSyncBuffer != null && toBeSyncBuffer != buf) {
			toBeSyncBuffer.release();
		}
		toBeSyncBuffer = buf;
		shouldSync = true;
	}

	public void doingSyncIfNecessary(Consumer<LotsOfByteBuffer> c) {
		synchronized (this) {
			if (shouldSync) {
				LotsOfByteBuffer lastBuffer = this.currentBuffer;

				currentBuffer = toBeSyncBuffer;
				toBeSyncBuffer = null;
				c.accept(this.currentBuffer);

				if (lastBuffer != null && lastBuffer != currentBuffer) {
					currentBuffer.release();
				}

				shouldSync = false;
			}
		}
	}

	public interface BackBuffer {

		public LotsOfByteBuffer getBuffer();

		public void markSync();

	}

}
