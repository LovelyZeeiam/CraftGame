package xueLi.utils.bilibili;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;

import xueLi.utils.bilibili.gsonTemplate.TUpperRelation;

public class BilibiliAPI {

	public static Gson gson = new Gson();

	public static boolean realtimeGetFansRunning = true;
	public static long follower;

	private static final Thread realtimeGetFans = new Thread(() -> {
		while (realtimeGetFansRunning) {
			try {
				follower = BilibiliAPI.getBilibiliUpperFollower(157262276);
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

	public static long getBilibiliUpperFollower(long uuid) throws IOException {
		URL url = new URL("http://api.bilibili.com/x/relation/stat?vmid=" + uuid);
		InputStream in = url.openStream();
		TUpperRelation r = gson.fromJson(new InputStreamReader(in), TUpperRelation.class);
		in.close();
		return r.data.follower;
	}

}
