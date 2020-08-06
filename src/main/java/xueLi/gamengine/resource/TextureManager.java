package xueLi.gamengine.resource;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import xueLi.gamengine.gui.GUIManager;
import xueLi.gamengine.gui.GUIProgressBar;
import xueLi.gamengine.gui.GUITextView;

public class TextureManager implements Closeable {

	private String resourcePath;

	private Gson gson = new Gson();

	private JsonObject textureJsonObject;

	private GUIManager guiRenderer;

	public TextureManager(String path, GUIManager guiRenderer) {
		this.resourcePath = path;
		try {
			textureJsonObject = gson.fromJson(new FileReader(new File(path + "textures/textures.json")),
					JsonObject.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}

		this.guiRenderer = guiRenderer;

	}

	private HashMap<String, Texture> textures = new HashMap<String, Texture>();

	private void loadTexture(String name, JsonElement element, boolean preload) {
		String pathString = this.resourcePath + element.getAsString();
		if (name.startsWith("gui.")) {
			int id = guiRenderer.loadTexture(pathString);
			Texture texture = new Texture(id, preload, true);
			textures.put(name, texture);
		} else {
			// TODO: 一般材质读取

		}

	}

	public void preload() {
		JsonObject loadObject = textureJsonObject.get("preload").getAsJsonObject();
		loadObject.entrySet().forEach(entry -> {
			loadTexture(entry.getKey(), entry.getValue(), true);
		});
	}

	public void load() {
		JsonObject loadObject = textureJsonObject.get("load").getAsJsonObject();
		loadObject.entrySet().forEach(entry -> {
			loadTexture(entry.getKey(), entry.getValue(), false);
		});
	}

	public void load(GUIProgressBar progressBar, float startValue, float endValue) {
		JsonObject loadObject = textureJsonObject.get("load").getAsJsonObject();

		Set<Entry<String, JsonElement>> entrys = loadObject.entrySet();
		float progressPerElement = (endValue - startValue) / entrys.size();
		int count = 0;

		for (Entry<String, JsonElement> entry : entrys) {
			loadTexture(entry.getKey(), entry.getValue(), false);

			++count;
			progressBar.setProgress(startValue + progressPerElement * count);

		}
		;

		progressBar.setProgress(endValue);

	}

	public void load(GUITextView textView, GUIProgressBar progressBar, float startValue, float endValue) {
		JsonObject loadObject = textureJsonObject.get("load").getAsJsonObject();

		Set<Entry<String, JsonElement>> entrys = loadObject.entrySet();
		float progressPerElement = (endValue - startValue) / entrys.size();
		int count = 0;

		for (Entry<String, JsonElement> entry : entrys) {
			loadTexture(entry.getKey(), entry.getValue(), false);

			++count;
			progressBar.setProgress(startValue + progressPerElement * count);

			textView.setText("Loading... : " + entry.getKey());

		}
		;

		progressBar.setProgress(endValue);

	}

	public Texture getTexture(String name) {
		return textures.get(name);
	}

	/**
	 * 会删除所有资源除了preload
	 */
	@Override
	public void close() {

	}

	/**
	 * 真正的删除所有资源
	 */
	public void release() {

	}

}
