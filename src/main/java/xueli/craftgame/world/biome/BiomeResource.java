package xueli.craftgame.world.biome;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonObject;

import xueli.gamengine.resource.IResource;
import xueli.gamengine.resource.LangManager;
import xueli.gamengine.utils.Logger;
import xueli.gamengine.utils.store.Color;

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
		data.namespace = namespace;

		data.leaves_color_wrapper = new Color(data.leaves_color);
		data.map_color_wrapper = new Color(data.map_color);
		data.water_color_wrapper = new Color(data.water_color);
		
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
