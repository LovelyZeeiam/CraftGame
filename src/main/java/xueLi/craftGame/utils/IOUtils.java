package xueLi.craftGame.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.gson.Gson;
import xueLi.craftGame.template.bilibili.TUpperRelation;

public class IOUtils {

	private static Gson gson = new Gson();

	public static long getBilibiliUpperFollower(long uuid) throws IOException {
		URL url = new URL("http://api.bilibili.com/x/relation/stat?vmid=" + uuid);
		InputStream in = url.openStream();
		TUpperRelation r = gson.fromJson(new InputStreamReader(in), TUpperRelation.class);
		in.close();
		return r.data.follower;
	}

	public static String readAllToString(String path) throws IOException {
		FileInputStream s = new FileInputStream(path);
		byte[] b = new byte[s.available()];
		s.read(b);
		s.close();
		return new String(b);
	}

}
