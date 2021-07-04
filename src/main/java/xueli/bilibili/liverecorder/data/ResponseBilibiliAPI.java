package xueli.bilibili.liverecorder.data;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import xueli.utils.io.Webs;

public class ResponseBilibiliAPI<T> {

	public int code;
	public String message;
	public int ttl;
	public T data;

	public static ResponseRoomInfo fetchInfo(long uuid, String sessData) throws JsonSyntaxException, IOException {
		String liveroomUrl = "http://api.live.bilibili.com/room/v1/Room/getRoomInfoOld?mid=" + uuid;
		ResponseRoomInfo info = new Gson().fromJson(Webs.readAllFromWebString(liveroomUrl, c -> {
			c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0");
			c.setRequestProperty("host", "api.live.bilibili.com");
			c.setRequestProperty("Cookie", "SESSDATA=" + sessData);
		}), ResponseRoomInfo.class);
		return info;
	}

}
