package xueli.game2.memory;

import org.junit.jupiter.api.Test;

public class CByteVectorTest {
	
	@Test
	public void addTest() {
		CByteVector vector = new CByteVector(1000000, 1000000);
		for (int i = 0; i < 99999999; i++) {
			vector.put((byte) 0);
			if(i % 10000000 == 0) {
				System.out.println(vector);
			}
		}
		vector.release();
	}
	
	@Test
	public void releaseTest() {
		for(int j = 0; j < 1000; j++) {
			CByteVector vector2 = new CByteVector(10000, 100);
			vector2.release();
			
			if(j % 1000 == 0) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	@Test
	public void leakTest() {
		for(int j = 0; j < 100; j++) {
			new CByteVector(100000, 100);
//			vector2.release();
			
			if(j % 10 == 0) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public static void main(String[] args) {
		new CByteVectorTest().leakTest();
	}
	
}
