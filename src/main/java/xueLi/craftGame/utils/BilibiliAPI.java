package xueLi.craftGame.utils;

import java.io.IOException;

public class BilibiliAPI {
	
	public static boolean realtimeGetFansRunning = true;
	public static long follower;
	
	private static final Thread realtimeGetFans = new Thread(()-> {
		while(realtimeGetFansRunning) {
		try {
			follower = FileIO.getBilibiliUpperFollower(157262276);
			System.out.println("宁的粉丝数: " + follower);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	});
	
	public static void startThreadOfRealtimeGetFans() {
		realtimeGetFans.start();
	}
	
	public static void stopThreadOfRealtimeGetFans() {
		realtimeGetFansRunning = false;
	}

}
