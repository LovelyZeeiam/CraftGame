package xueli.gamengine.resource;

import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_FLIPY;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_PREMULTIPLIED;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_REPEATX;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_REPEATY;

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

import xueli.gamengine.utils.Logger;
import xueli.gamengine.utils.vector.Vector2s;
import xueli.gamengine.view.ViewManager;

public class TextureManager implements Closeable {

	private String resourcePath;

	private Gson gson = new Gson();

	private JsonObject textureJsonObject;

	private ViewManager guiRenderer;
	private HashMap<String, Texture> textures = new HashMap<String, Texture>();

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

	private void loadTexture(String name, JsonElement element, boolean preload) {
		String pathString = null;

		boolean isAtlas = false;

		if (element.isJsonObject()) {
			if (element.getAsJsonObject().has("path"))
				pathString = this.resourcePath + element.getAsJsonObject().get("path").getAsString();
			else
				isAtlas = true;
		} else
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
			int[] pixels = null;
			int width = 0, height = 0;

			// For Atlas:
			HashMap<String, Vector2s> atlas = new HashMap<String, Vector2s>();
			// 材质册中单个图片的大小
			int singlePictureSize = 0;
			// 材质册是正方形的 这个记录了横竖都有几个图片
			int length = 0;

			if (isAtlas) {
				JsonObject atlasJsonObject = element.getAsJsonObject();

				Set<Entry<String, JsonElement>> entrySet = atlasJsonObject.entrySet();
				int size = entrySet.size();

				length = new Double(Math.ceil(Math.sqrt(size))).intValue();

				int[][] pixelss = new int[size][];
				int count = 0;

				// 图片类型
				int picture_type = -1;

				for (Entry<String, JsonElement> entry : entrySet) {
					String namespace = entry.getKey();
					String path = this.resourcePath + entry.getValue().getAsString();

					BufferedImage image = null;
					try {
						image = ImageIO.read(new File(path));
						width = image.getWidth();
						height = image.getHeight();
						picture_type = image.getType();
						pixelss[count] = new int[width * height];
						image.getRGB(0, 0, width, height, pixelss[count], 0, width);

						singlePictureSize = Math.max(singlePictureSize, Math.max(width, height));

					} catch (IOException e) {
						e.printStackTrace();
					}

					atlas.put(namespace, new Vector2s(new Integer(count / length).shortValue(),
							new Integer(count % length).shortValue()));

					++count;
				}

				BufferedImage image = new BufferedImage(singlePictureSize * length, singlePictureSize * length,
						BufferedImage.TYPE_4BYTE_ABGR);

				count = 0;
				for (; count < size; ++count) {
					int x = count / length;
					int y = count % length;

					image.setRGB(x * singlePictureSize, y * singlePictureSize, singlePictureSize, singlePictureSize,
							pixelss[count], 0, width);

				}

				width = image.getWidth();
				height = image.getHeight();
				pixels = new int[width * height];
				image.getRGB(0, 0, width, height, pixels, 0, width);

				try {
					ImageIO.write(image, "png", new File("a.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				try {
					BufferedImage image = ImageIO.read(new File(pathString));
					width = image.getWidth();
					height = image.getHeight();
					pixels = new int[width * height];
					image.getRGB(0, 0, width, height, pixels, 0, width);
				} catch (IOException e) {
					Logger.error(e.getMessage() + ": " + pathString);
				}

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
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA,
					GL11.GL_UNSIGNED_BYTE, data);
			Texture texture = null;
			if (!isAtlas) {
				texture = new Texture(id, preload, false);
			} else {
				texture = new TextureAtlas(atlas, length, length, id, preload, false);
			}
			texture.width = width;
			texture.height = height;
			textures.put(name, texture);

		}

	}

	public void load() {
		JsonObject loadObject = textureJsonObject.get("textures").getAsJsonObject();
		loadObject.entrySet().forEach(entry -> {
			JsonElement element = entry.getValue();
			String nameString = entry.getKey();
			loadTexture(nameString, element, false);
		});
	}

	public Texture getTexture(String name) {
		return textures.get(name);
	}

	@Override
	public void close() {
		for (Texture texture : textures.values()) {
			// According to src of nanovg, when call 'nvgDelete' nanovg itself deletes all
			// the texture registered using its method
			if (!texture.isGUITexture) {
				GL11.glDeleteTextures(texture.id);

			}
		}
	}

	public HashMap<String, Texture> getTextures() {
		return textures;
	}

}
