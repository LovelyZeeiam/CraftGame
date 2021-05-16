package xueli.musicjson.nbs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class NBSConverter {

	public static void main(String[] args) throws IOException {
		convert("D:\\eclipse-java-workspace\\CraftGame\\res\\music\\ok x lionhearted.nbs", "D:\\\\eclipse-java-workspace\\\\CraftGame\\\\res\\\\music\\\\ok x lionhearted.json");

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
