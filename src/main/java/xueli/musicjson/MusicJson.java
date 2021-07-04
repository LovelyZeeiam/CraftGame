package xueli.musicjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import xueli.game.sound.SoundManager;

public class MusicJson {

	private static Logger logger = Logger.getLogger(MusicJson.class.getName());

	private static Gson gson = new Gson();
	private static SoundManager manager;

	static {
		manager = new SoundManager();
		manager.init();

	}

	private String name;
	private String[] authors;
	private float bpm;
	private HashMap<Integer, ArrayList<Note>> notes = new HashMap<>();
	private int maxTime = 0;

	public MusicJson(String data) {
		JsonObject main_json = gson.fromJson(data, JsonObject.class);

		checkJsonHas("format_version", main_json);
		int version = main_json.get("format_version").getAsInt();
		switch (version) {
		case 0:
			read_version_0(main_json);
			break;
		default:
			logger.severe("[MusicJson] Version " + version + " not supported!");
			break;
		}

	}

	private void read_version_0(JsonObject data) {
		checkJsonHas("name", data);
		checkJsonHas("bpm", data);
		checkJsonHas("author", data);

		// 获取name
		this.name = data.get("name").getAsString();
		// 获取authors
		JsonArray authorJson = data.getAsJsonArray("author");
		this.authors = new String[authorJson.size()];
		for (int i = 0; i < authorJson.size(); i++)
			this.authors[i] = authorJson.get(i).getAsString();
		// 获取bpm
		this.bpm = data.get("bpm").getAsInt();

		JsonArray dataJson = data.get("data").getAsJsonArray();

		float beatPerSecond = 60.0f / bpm;

		dataJson.forEach((e) -> {
			JsonObject o = e.getAsJsonObject();

			int time = (int) (o.get("time").getAsInt() * beatPerSecond * 1000 / 4) + 100;
			String type = o.get("type").getAsString();
			int note = o.get("note").getAsInt();
			float rate = (float) Math.pow(2, (note) / 12.0);

			Note n = new Note(type, rate);
			if (!notes.containsKey(time))
				notes.put(time, new ArrayList<>());
			notes.get(time).add(n);

			maxTime = Math.max(maxTime, time);

		});

	}

	private void checkJsonHas(String key, JsonObject object) {
		if (!object.has(key)) {
			throw new RuntimeException("[MusicJson] Can't find param " + key + "!");
		}

	}

	public void play() {
		long startTime = System.currentTimeMillis();

		int lastTimeNote = 0;

		while (true) {
			int duration = (int) (System.currentTimeMillis() - startTime);

			for (int time = lastTimeNote + 1; time <= duration; time++) {
				ArrayList<Note> tickNotes = notes.get(time);

				if (tickNotes != null) {
					tickNotes.forEach(n -> {
						manager.play("note." + n.getType(), 1.0f, n.getRate());
					});
				}

			}

			lastTimeNote = duration;

			if (duration > maxTime + 1000)
				break;

			if (duration % 1000 < 100) {
				manager.tick();

			}

		}

	}

	public void release() {
		manager.release();

	}

	public String getName() {
		return name;
	}

	public String[] getAuthors() {
		return authors;
	}

	public float getBpm() {
		return bpm;
	}

	public HashMap<Integer, ArrayList<Note>> getNotes() {
		return notes;
	}

}
