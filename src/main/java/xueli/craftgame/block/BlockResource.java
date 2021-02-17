package xueli.craftgame.block;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import xueli.craftgame.CraftGame;
import xueli.gamengine.resource.IResource;
import xueli.gamengine.resource.LangManager;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.resource.TextureManager;
import xueli.gamengine.utils.vector.Vector2s;
import xueli.utils.Logger;

public class BlockResource extends IResource {

	private HashMap<String, BlockData> dataMap = new HashMap<String, BlockData>();

	private LangManager langManager;
	private TextureManager textureManager;

	public BlockResource(String path) {
		super(path + "/blocks/");
		if (CraftGame.INSTANCE_CRAFT_GAME != null) {
			this.langManager = CraftGame.INSTANCE_CRAFT_GAME.getLangManager();
			this.textureManager = CraftGame.INSTANCE_CRAFT_GAME.getTextureManager();

		}

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

			BlockData blockData = new BlockData(namespace, name, CraftGame.INSTANCE_CRAFT_GAME == null ? null
					: CraftGame.INSTANCE_CRAFT_GAME.getModelManager().getModel(modelName));
			dataMap.put(namespace, blockData);

			Logger.info("Blocks: read Block Defination File: " + file.getName());

		}

	}

	public void load() {
		ArrayList<File> blocksFiles = findAllFiles(new File(pathString));

		Logger.info("Blocks: find Block Defination File: " + blocksFiles.size());

		for (File f : blocksFiles) {
			load(f);

		}

	}

	public BlockData getBlockData(String namespace) {
		return dataMap.get(namespace);
	}

}
