package xueLi.gamengine.resource;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import java.util.Set;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import xueLi.gamengine.utils.Logger;
import xueLi.gamengine.view.GUIProgressBar;
import xueLi.gamengine.view.GUITextView;
import xueLi.gamengine.view.ViewManager;

import static org.lwjgl.nanovg.NanoVG.*;

public class TextureManager implements Closeable {

	private String resourcePath;

	private Gson gson = new Gson();

	private JsonObject textureJsonObject;

	private ViewManager guiRenderer;

	public TextureManager(String path, ViewManager guiRenderer) {
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
		String pathString = null;
		if (element.isJsonObject())
			pathString = this.resourcePath + element.getAsJsonObject().get("path").getAsString();
		else
			pathString = this.resourcePath + element.getAsString();

		if (name.startsWith("gui.")) {
			int flag = 0;
			if (element.isJsonObject()) {
				JsonObject object = element.getAsJsonObject();
				for (JsonElement e : object.get("flag").getAsJsonArray()) {
					switch (e.getAsString()) {
					case "repeat_x":
						flag |= NVG_IMAGE_REPEATX;
						break;
					case "repeat_y":
						flag |= NVG_IMAGE_REPEATY;
						break;
					case "flipy":
						flag |= NVG_IMAGE_FLIPY;
						break;
					case "premultiplied":
						flag |= NVG_IMAGE_PREMULTIPLIED;
						break;
					case "nearest":
						flag |= NVG_IMAGE_NEAREST;
					default:
						break;
					}
				}
			}

			int id = guiRenderer.loadTexture(pathString, flag);
			if (id == 0) {
				Logger.error("[Texture] Can't load texture: " + name);
			} else {
				Texture texture = new Texture(id, preload, true);
				textures.put(name, texture);
			}
		} else {
			// TODO: 一般材质读取系列
			int[] pixels = null;
			int width = 0, height = 0;
			try {
				BufferedImage image = ImageIO.read(new File(pathString));
				width = image.getWidth();
				height = image.getHeight();
				pixels = new int[width * height];
				image.getRGB(0, 0, width, height, pixels, 0, width);
			} catch (IOException e) {
				e.printStackTrace();
			}
			int[] data = new int[width * height];
			for (int i = 0; i < width * height; i++) {
				int a = (pixels[i] & 0xff000000) >> 24;
				int r = (pixels[i] & 0xff0000) >> 16;
				int g = (pixels[i] & 0xff00) >> 8;
				int b = (pixels[i] & 0xff);
				data[i] = a << 24 | b << 16 | g << 8 | r;
			}
			int id = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 4);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,
					data);
			
			Texture texture = new Texture(id, preload, false);
			textures.put(name, texture);
			

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

		String loading_messageString = textView.getText();

		for (Entry<String, JsonElement> entry : entrys) {
			loadTexture(entry.getKey(), entry.getValue(), false);

			++count;
			progressBar.setProgress(startValue + progressPerElement * count);

			textView.setText(loading_messageString + " - " + entry.getKey());

		}

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
