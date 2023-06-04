package xueli.game2.memory;

import org.junit.jupiter.api.Test;

import xueli.utils.collection.DirectArrayList;

public class DirectArrayListTest {

	@Test
	public void addTest() {
		DirectArrayList vector = new DirectArrayList(1000000, 1000000);
		for (int i = 0; i < 99999999; i++) {
			vector.putByte((byte) 0);
			if (i % 10000000 == 0) {
				System.out.println(vector);
			}
		}
		vector.release();
	}

	@Test
	public void releaseTest() {
		for (int j = 0; j < 1000; j++) {
			DirectArrayList vector2 = new DirectArrayList(10000, 100);
			vector2.release();

			if (j % 1000 == 0) {
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
//		for(int j = 0; j < 100; j++) {
//			new CVector(100000, 100);
//			
//			if(j % 10 == 0) {
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			
//		}
		System.out.println("Just for development test, so I disabled it.");

	}

}
