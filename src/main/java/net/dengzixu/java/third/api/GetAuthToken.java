package net.dengzixu.java.third.api;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetAuthToken {
	public static final String URL = "https://api.live.bilibili.com/room/v1/Danmu/getConf?room_id=";

	String data = null;

	@SuppressWarnings("unchecked")
	public static String get(long roomId) {
		OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

		Request request = new Request.Builder().get().url(URL + roomId).build();

		try {
			Response response = okHttpClient.newCall(request).execute();
			if (response.code() != 200) {
				return null;
			}

			String body = Objects.requireNonNull(response.body()).string();

			if ("".equals(body)) {
				return null;
			}

//            System.out.println(body);

			ObjectMapper objectMapper = new ObjectMapper();

			Map<String, Object> bodyMap = objectMapper.readValue(body, Map.class);

			Map<String, Object> dataMap = (Map<String, Object>) bodyMap.get("data");

			return (String) dataMap.get("token");
//            System.out.println(Objects.requireNonNull(response.body()).string());
		} catch (NullPointerException | IOException e) {
			return null;
		}

	}
}
