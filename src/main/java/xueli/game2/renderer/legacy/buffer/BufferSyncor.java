package xueli.game2.renderer.legacy.buffer;

import java.util.function.Consumer;

public class BufferSyncor {
    
    LotsOfByteBuffer currentBuffer;
    private boolean shouldSync = false;

    private LotsOfByteBuffer toBeSyncBuffer;

    public BufferSyncor() {
    }

    public synchronized BackBuffer createBackBuffer() {
        if(toBeSyncBuffer != null) {
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

    public LotsOfByteBuffer getLatestBuffer() {
        return toBeSyncBuffer == null ? currentBuffer : toBeSyncBuffer;
    }

    public synchronized void updateBuffer(LotsOfByteBuffer buf) {
        if(toBeSyncBuffer != null && toBeSyncBuffer != buf) {
            toBeSyncBuffer.release();
        }
        toBeSyncBuffer = buf;
        shouldSync = true;
    }

    public synchronized void doingSyncIfNecessary(Consumer<LotsOfByteBuffer> c) {
        if(shouldSync) {
            if(currentBuffer != null && toBeSyncBuffer != currentBuffer) {
                currentBuffer.release();
            }
            currentBuffer = toBeSyncBuffer;
            toBeSyncBuffer = null;
            c.accept(this.currentBuffer);
            
            shouldSync = false;
        }
    }

    public interface BackBuffer {

        public LotsOfByteBuffer getBuffer();
        public void markSync();

    }
    
}
