package xueli.utils.buffer;

// Suddenly Xueli remember that C# has something similar :}
public interface MemoryHandler {
	
	public LotsOfByteBuffer getBuffer();
	public void release();
	
}