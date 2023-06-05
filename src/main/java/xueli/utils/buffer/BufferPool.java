package xueli.utils.buffer;

import java.util.ArrayList;
import java.util.LinkedList;

// The memory is managed by the pool!
// Not thread safe :{
public class BufferPool implements AutoCloseable {
	
	private final int perSize;
	
	private final LinkedList<LotsOfByteBuffer> allocated = new LinkedList<>();
	private final LinkedList<LotsOfByteBuffer> freed = new LinkedList<>();
	
	private final ArrayList<BufferPoolListener> listeners = new ArrayList<>();
	
	public BufferPool(int perSize) {
		this.perSize = perSize;
	}
	
	public void initialSpare(int initialCount) {
		for(int i = 0; i < initialCount; i++) {
			this.spareMore();
		}
	}
	
	private void spareMore() {
		var newBuffer = new LotsOfByteBuffer(this.perSize);
		freed.add(newBuffer);
		listeners.forEach(l -> l.onNewBufferAllocated(newBuffer));
		
	}
	
	public MemoryHandler spare() {
		if(freed.size() == 0) {
			this.spareMore();
		}
		
		final var buffer = freed.remove(freed.size() - 1);
		
		// This is the solution of a annoying bug!
		buffer.setReadWrite(false);
		buffer.clear();
		
		allocated.add(buffer);
		
		listeners.forEach(l -> l.onBufferMarkUsing(buffer));
		
		return new MemoryHandler() {
			
			@Override
			public LotsOfByteBuffer getBuffer() {
				return buffer;
			}
			
			@Override
			public void release() {
				allocated.remove(buffer);
				freed.add(buffer);
				listeners.forEach(l -> l.onBufferRecycled(buffer));
			}
			
		};
	}
	
	public void addListener(BufferPoolListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(BufferPoolListener listener) {
		this.listeners.remove(listener);
	}
	
	private void freeBuffer(LotsOfByteBuffer buffer) {
		listeners.forEach(l -> l.beforeBufferReleased(buffer));
		buffer.release();
		
	}
	
	public int getAllocatedCount() {
		return this.allocated.size();
	}
	
	public int getFreeCount() {
		return this.freed.size();
	}
	
	@Override
	public void close() throws Exception {
		this.allocated.forEach(this::freeBuffer);
		this.freed.forEach(this::freeBuffer);
		
	}
	
}