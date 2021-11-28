package xueli.musicjson.nbs;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class NBSConverter {

	public static void main(String[] args) throws IOException {
		convert("res/music/aurora.nbs", "res/music/aurora.json");

	}

	public static void convert(String inPath, String outPath) throws IOException {
		NBSInputStream in = new NBSInputStream(new BufferedInputStream(new FileInputStream(new File(inPath))));
		List<NoteBlock> blocks = in.readNoteBlocks();
		in.close();

		JsonObject mainJson = new JsonObject();
		mainJson.addProperty("format_version", 0);
		mainJson.addProperty("name", in.getName());

		float tickPerSecond = (float) in.getTempo() / 100.0f;
		float beatPerMinute = tickPerSecond * 60.0f / 4.0f;
		mainJson.addProperty("bpm", beatPerMinute);

		JsonArray authors = new JsonArray();
		Arrays.stream(in.getAuthor().split(",")).forEach(authors::add);
		Arrays.stream(in.getOriginAuthor().split(",")).forEach(authors::add);
		mainJson.add("author", authors);

		JsonArray datas = new JsonArray();

		blocks.forEach((b) -> {
			JsonObject dataObj = new JsonObject();
			dataObj.addProperty("time", b.getTick());
			dataObj.addProperty("type", b.getInst());
			dataObj.addProperty("note", b.getKey() - 45);
			datas.add(dataObj);
		});

		mainJson.add("data", datas);

		File outfile = new File(outPath);
		if (!outfile.exists())
			outfile.createNewFile();

		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outfile));
		out.write(mainJson.toString().getBytes());
		out.flush();
		out.close();

	}

}
