package xueli.utils.cache;

import com.google.gson.*;
import xueli.utils.io.Files;
import xueli.utils.io.Webs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class NetResourceCache {

	private String path;

	private HashMap<String, String> map = new HashMap<>();
	private HashMap<String, byte[]> caches = new HashMap<>();

	public NetResourceCache(String path) {
		this.path = path;

		try {
			readInfoFile();
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				saveInfoFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));

	}

	protected byte[] getWhenNotFoundInLocal(String url) throws IOException {
		return Webs.readAllFromWeb(url);
	}

	public byte[] get(String url) throws IOException {
		if (map.containsKey(url)) {
			if (caches.containsKey(url)) {
				return caches.get(url);
			}
			try {
				byte[] b = Files.readAllByte(new File(path + "/" + map.get(url)));
				caches.put(url, b);
				return b;
			} catch (IOException e) {
			}
		}

		byte[] all = getWhenNotFoundInLocal(url);
		String name = getRandomString(30);
		map.put(url, name);

		Files.fileOutput(path + "/" + name, all);
		return all;
	}

	private static String getRandomString(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	private void readInfoFile() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		JsonObject obj = new Gson().fromJson(new FileReader(new File(path + "/info.json")), JsonObject.class);
		JsonArray array = obj.getAsJsonArray("lists");
		array.forEach(e -> {
			JsonObject entry = e.getAsJsonObject();
			String name = entry.get("name").getAsString();
			String url = entry.get("url").getAsString();
			map.put(url, name);

		});

	}

	private void saveInfoFile() throws IOException {
		JsonObject obj = new JsonObject();
		JsonArray array = new JsonArray();

		map.forEach((url, name) -> {
			JsonObject entry = new JsonObject();
			entry.add("name", new JsonPrimitive(name));
			entry.add("url", new JsonPrimitive(url));
			array.add(entry);
		});

		obj.add("lists", array);
		Files.fileOutput(path + "/info.json", obj.toString());

	}

	public void clearCacheInMemory() {
		caches.clear();
	}

	public static NetResourceCache getInstance() {
		return new NetResourceCache("./cache/");
	}

}