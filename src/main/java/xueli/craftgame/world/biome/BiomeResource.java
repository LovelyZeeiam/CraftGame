package xueli.craftgame.world.biome;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.nanovg.NanoVG;

import com.google.gson.JsonObject;

import xueli.gamengine.resource.IResource;
import xueli.gamengine.resource.LangManager;
import xueli.gamengine.utils.Logger;

public class BiomeResource extends IResource {

	public static HashMap<String, BiomeData> biomes = new HashMap<>();

	private LangManager lang;
	private String realPath;

	public BiomeResource(String pathString, LangManager lang) {
		super(pathString);
		this.lang = lang;
		this.realPath = pathString + "biomes/";

	}

	private void load(File f) {
		JsonObject root = null;
		try {
			root = gson.fromJson(new FileReader(f), JsonObject.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String name = lang.getStringFromLangMap(root.get("name").getAsString());
		String namespace = root.get("namespace").getAsString();
		BiomeData data = gson.fromJson(root.get("biomes"), BiomeData.class);
		data.name = name;

		NanoVG.nvgRGB(data.map_color[0], data.map_color[1], data.map_color[2], data.map_color_nvg);
		NanoVG.nvgRGB(data.water_color[0], data.water_color[1], data.water_color[2], data.water_color_nvg);
		NanoVG.nvgRGB(data.leaves_color[0], data.leaves_color[1], data.leaves_color[2], data.leaves_color_nvg);

		biomes.put(namespace, data);
		Logger.info("Biomes: read Biome Defination file: " + f.getName());

	}

	public void load() {
		ArrayList<File> blocksFiles = findAllFiles(new File(realPath));

		Logger.info("Biomes: find Biome Defination file: " + blocksFiles.size());

		for (File f : blocksFiles) {
			load(f);
		}

	}

	@Override
	public void close() throws IOException {

	}
}
