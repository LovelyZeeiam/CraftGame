package xueli.game2.renderer.legacy.buffer;

import org.junit.jupiter.api.Test;

import xueli.utils.buffer.LotsOfByteBuffer;

public class BufferTest {
	
	@Test
	public void releaseTest() {
		for(int i = 0; i < 10; i++) {
			LotsOfByteBuffer l = new LotsOfByteBuffer(1000000000);
			l.release();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
