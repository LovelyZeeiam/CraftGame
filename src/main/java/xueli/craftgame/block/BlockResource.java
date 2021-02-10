package xueli.craftgame.block;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import xueli.craftgame.block.rendercontrol.model.Models;
import xueli.gamengine.resource.*;
import xueli.gamengine.utils.Logger;
import xueli.gamengine.utils.vector.Vector2s;
import xueli.gamengine.view.GUIProgressBar;
import xueli.gamengine.view.GUITextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class BlockResource extends IResource {

	public static HashMap<String, BlockData> blockDatas = new HashMap<String, BlockData>();

	private LangManager langManager;
	private TextureManager textureManager;
	private String real_path;

	public BlockResource(String pathString, LangManager langManager, TextureManager textureManager) {
		super(pathString);
		this.real_path = pathString + "blocks/";

		this.langManager = langManager;
		this.textureManager = textureManager;

	}

	public BlockResource(String pathString, LangManager lang) {
		super(pathString);
		this.real_path = pathString + "blocks/";

		this.langManager = lang;

	}

	private void load(File file) {
		JsonObject jsonObject = null;
		try {
			jsonObject = gson.fromJson(new FileReader(file), JsonObject.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}

		if (!jsonObject.has("namespace")) {
			Logger.error("[Block] Can't read param in " + file.getName() + ": namespace");
			return;
		}
		String namespace = langManager.getStringFromLangMap(jsonObject.get("namespace").getAsString());
		if (!jsonObject.has("name")) {
			Logger.error("[Block] Can't read param in " + file.getName() + ": name");
			return;
		}
		String name = langManager.getStringFromLangMap(jsonObject.get("name").getAsString());

		if (jsonObject.has("block")) {
			JsonObject blockJsonObject = jsonObject.getAsJsonObject("block");

			if (!blockJsonObject.has("type")) {
				Logger.error("[Block] Can't read param of block in " + file.getName() + ": type");
				return;
			}
			BlockType blockType = BlockType.valueOf(blockJsonObject.get("type").getAsString());
			if (!blockJsonObject.has("destroyTime")) {
				Logger.error("[Block] Can't read param of block in " + file.getName() + ": destroyTime");
				return;
			}
			int destroyTime = blockJsonObject.get("destroyTime").getAsInt();

			Vector2s[] textures = new Vector2s[6];

			if (this.textureManager != null) {
				if (!blockJsonObject.has("texture")) {
					Logger.error("[Block] Can't read param of block in " + file.getName() + ": texture");
					return;
				}
				JsonElement blockTexture = blockJsonObject.get("texture");
				if (blockTexture.isJsonObject()) {
					JsonObject textureObject = blockTexture.getAsJsonObject();
					textures[0] = ((TextureAtlas) textureManager.getTexture("blocks"))
							.getAtlasID(textureObject.get("front").getAsString());
					textures[1] = ((TextureAtlas) textureManager.getTexture("blocks"))
							.getAtlasID(textureObject.get("back").getAsString());
					textures[2] = ((TextureAtlas) textureManager.getTexture("blocks"))
							.getAtlasID(textureObject.get("left").getAsString());
					textures[3] = ((TextureAtlas) textureManager.getTexture("blocks"))
							.getAtlasID(textureObject.get("right").getAsString());
					textures[4] = ((TextureAtlas) textureManager.getTexture("blocks"))
							.getAtlasID(textureObject.get("top").getAsString());
					textures[5] = ((TextureAtlas) textureManager.getTexture("blocks"))
							.getAtlasID(textureObject.get("bottom").getAsString());

				} else {
					// 单个材质六面
					Vector2s textureID = ((TextureAtlas) textureManager.getTexture("blocks"))
							.getAtlasID(blockTexture.getAsString());
					textures[0] = textures[1] = textures[2] = textures[3] = textures[4] = textures[5] = textureID;
				}

			}

			String modelName = null;
			if (blockJsonObject.has("model")) {
				modelName = blockJsonObject.get("model").getAsString();
			} else {
				modelName = "cube";
			}

			JsonObject renderArgs = null;
			if (blockJsonObject.has("renderer_args")) {
				renderArgs = blockJsonObject.getAsJsonObject("renderer_args");
			}

			byte[] mapColor = GuiResource.loadColorToByteArray(blockJsonObject.getAsJsonArray("map_color"));

			BlockData blockData = new BlockData(namespace, name, blockType, destroyTime, textures, mapColor,
					Models.getModel(modelName, renderArgs));
			blockDatas.put(namespace, blockData);

			Logger.info("Blocks: read Block Defination File: " + file.getName());

		}

	}

	public void load(GUITextView textView, GUIProgressBar progressBar, float startValue, float endValue) {
		ArrayList<File> blocksFiles = findAllFiles(new File(real_path));

		Logger.info("Blocks: find Block Defination File: " + blocksFiles.size());

		int count = 0;
		float progressPerElement = (endValue - startValue) / blocksFiles.size();

		String loading_textString = textView.getText();

		for (File f : blocksFiles) {
			load(f);

			++count;
			progressBar.setProgress(startValue + progressPerElement * count);

			textView.setText(loading_textString + " - " + f.getName());

		}

		progressBar.setProgress(endValue);

	}

	public void load() {
		ArrayList<File> blocksFiles = findAllFiles(new File(real_path));

		Logger.info("Blocks: find Block Defination File: " + blocksFiles.size());

		for (File f : blocksFiles) {
			load(f);

		}

	}

	@Override
	public void close() {

	}

}
