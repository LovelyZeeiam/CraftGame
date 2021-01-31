package xueli.gamengine.musicjson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import xueli.gamengine.utils.Entry;
import xueli.gamengine.utils.Logger;
import xueli.gamengine.utils.SoundManager;

@Deprecated
public class MusicJson {

	private static Gson gson = new Gson();

	static {
		try {
			Class.forName("xueli.gamengine.musicjson.NoteType");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String name;
	private String[] authors;
	private int bpm;
	private ArrayList<INoteCommand> notes = new ArrayList<>();

	public MusicJson(String data) {
		JsonObject main_json = gson.fromJson(data, JsonObject.class);

		checkJsonHas("format_version", main_json);
		int version = main_json.get("format_version").getAsInt();
		switch (version) {
		case 0:
			read_version_0(main_json);
			break;
		default:
			Logger.error(new Exception("[MusicJson] Version " + version + " not supported!"));
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
		// TreeMap<Integer, Note> notesCache = new TreeMap<>(Comparator.naturalOrder());
		ArrayList<Entry<Integer, Note>> notesCache = new ArrayList<>();

		float beatPerSecond = 60.0f / bpm;

		dataJson.forEach((e) -> {
			JsonObject o = e.getAsJsonObject();

			int time = (int) (o.get("time").getAsInt() * beatPerSecond * 1000 / 8);
			String type = o.get("type").getAsString();
			int note = o.get("note").getAsInt();
			float rate = (float) Math.pow(2, (note - 12.0) / 12.0);

			Note n = new Note(type, rate);
			notesCache.add(new Entry<Integer, Note>(time, n));

		});

		Collections.sort(notesCache, Comparator.comparingInt(Entry::getK));

		AtomicInteger lastTime = new AtomicInteger();
		notesCache.forEach((e) -> {
			int time = e.getK();

			int deltaTime = time - lastTime.get();
			if (deltaTime > 0)
				notes.add(new CommandWait(deltaTime));

			lastTime.set(time);

			notes.add(e.getV());

		});

	}

	private void checkJsonHas(String key, JsonObject object) {
		if (!object.has(key)) {
			Logger.error(new Exception("[MusicJson] Can't find param " + key + "!"));
		}

	}

	public void play() throws InterruptedException {
		AtomicInteger tick = new AtomicInteger();
		HashMap<Integer, ArrayList<Note>> readNotes = new HashMap<>();
		notes.forEach((c) -> {
			if (c instanceof Note) {
				ArrayList<Note> notes = readNotes.get(tick.intValue());
				if (notes == null) {
					notes = new ArrayList<>();
					readNotes.put(tick.intValue(), notes);
				}

				notes.add((Note) c);

			} else if (c instanceof CommandWait) {
				tick.addAndGet(((CommandWait) c).getWaitTime());

			}
		});

		long starttime = System.currentTimeMillis();
		while (true) {
			long dura = System.currentTimeMillis() - starttime;

			ArrayList<Note> ns = readNotes.get(dura);
			if (ns != null) {
				ns.forEach(n -> SoundManager.play(n.getType(), 1.0f, n.getRate()));
			}

			if (dura > tick.intValue())
				break;

		}

	}

	public void release() {
		NoteType.release();

	}

}
