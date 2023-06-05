package xueli.utils.buffer;

public interface BufferPoolListener {
	
	default public void onNewBufferAllocated(LotsOfByteBuffer buffer) {}
	default public void onBufferMarkUsing(LotsOfByteBuffer buffer) {}
	default public void onBufferRecycled(LotsOfByteBuffer buffer) {}
	default public void beforeBufferReleased(LotsOfByteBuffer buffer) {}
	
}