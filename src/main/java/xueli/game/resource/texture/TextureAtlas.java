package xueli.game.resource.texture;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import xueli.game.vector.Vector2i;
import xueli.utils.logger.MyLogger;

public class TextureAtlas {

	public static final String SINGLE = "S";

	private int width = 0, height = 0;
	private int id;
	private HashMap<String, Vector2i> atlas;

	TextureAtlas(HashMap<String, Vector2i> atlas, int width, int height, int id) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.atlas = atlas;

	}

	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void release() {
		GL11.glDeleteTextures(id);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Vector2i getAtlas(String key) {
		return atlas.get(key);
	}

	public TextureAtlasHolder getHolder(String namespace) {
		TextureAtlasHolder holder = new TextureAtlasHolder(namespace);
		holder.loadResult(this);
		return holder;
	}

	public static TextureAtlas single(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();

		int[] pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);

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

		HashMap<String, Vector2i> atlas = new HashMap<>();
		atlas.put(SINGLE, new Vector2i(0, 0));

		return new TextureAtlas(atlas, width, height, id);
	}

	public static TextureAtlas generateAtlas(TextureAtlasBuilder builder) {
		HashMap<String, URL> textureMaps = builder.textureMaps;

		String[] names = new String[textureMaps.size()];
		BufferedImage[] images = new BufferedImage[textureMaps.size()];

		int per_width = 0, per_height = 0;

		int count = 0;
		try {
			for (Entry<String, URL> e : textureMaps.entrySet()) {
				String name = e.getKey();
				URL imgUrl = e.getValue();
				BufferedImage image = ImageIO.read(imgUrl);
				if (image == null) {
					MyLogger.getInstance().warning("Can't read image: " + imgUrl.toString());
					continue;
				}
				per_width = Math.max(per_width, image.getWidth());
				per_height = Math.max(per_height, image.getHeight());

				names[count] = name;
				images[count] = image;
				count++;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int size = (int) Math.ceil(Math.sqrt(images.length));

		HashMap<String, Vector2i> atlas = new HashMap<>();

		BufferedImage atlasImage = new BufferedImage(per_width * size, per_height * size,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = atlasImage.createGraphics();

		for (int i = 0; i < images.length; i++) {
			BufferedImage image = images[i];

			int x = i % size;
			int y = i / size;
			atlas.put(names[i], new Vector2i(x, y));

			g2d.drawImage(image, x * per_width, y * per_height, null);

		}

		g2d.dispose();
		/*
		 * try { ImageIO.write(atlasImage, "png", new File("temp/atlas.png")); } catch
		 * (IOException e) { e.printStackTrace(); }
		 */

		int[] pixels = new int[atlasImage.getWidth() * atlasImage.getHeight()];
		atlasImage.getRGB(0, 0, atlasImage.getWidth(), atlasImage.getHeight(), pixels, 0, atlasImage.getWidth());

		int[] data = new int[atlasImage.getWidth() * atlasImage.getHeight()];
		for (int i = 0; i < atlasImage.getWidth() * atlasImage.getHeight(); i++) {
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
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, atlasImage.getWidth(), atlasImage.getHeight(), 0,
				GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);

		return new TextureAtlas(atlas, size, size, id);
	}

	public static TextureAtlas generateAtlas(String defineJsonPath, String textureFolderString) {
		JsonObject obj = null;
		try {
			obj = new Gson().fromJson(new JsonReader(new BufferedReader(new FileReader(defineJsonPath))),
					JsonObject.class);
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e1) {
			e1.printStackTrace();
		}
		JsonObject blocksObj = obj.get("blocks").getAsJsonObject();

		String[] names = new String[blocksObj.entrySet().size()];
		BufferedImage[] images = new BufferedImage[blocksObj.entrySet().size()];

		int per_width = 0, per_height = 0;

		int count = 0;
		try {
			for (Entry<String, JsonElement> e : blocksObj.entrySet()) {
				String name = e.getKey();
				String imgPath = e.getValue().getAsString();

				File imgFile = new File(textureFolderString + File.separator + imgPath);
				BufferedImage image = ImageIO.read(imgFile);
				if (image == null) {
					MyLogger.getInstance().warning("Can't read image: " + imgPath);
					continue;
				}
				per_width = Math.max(per_width, image.getWidth());
				per_height = Math.max(per_height, image.getHeight());

				names[count] = name;
				images[count] = image;
				count++;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int size = (int) Math.ceil(Math.sqrt(images.length));

		HashMap<String, Vector2i> atlas = new HashMap<>();

		BufferedImage atlasImage = new BufferedImage(per_width * size, per_height * size,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = atlasImage.createGraphics();

		for (int i = 0; i < images.length; i++) {
			BufferedImage image = images[i];

			int x = i % size;
			int y = i / size;
			atlas.put(names[i], new Vector2i(x, y));

			g2d.drawImage(image, x * per_width, y * per_height, null);

		}

		g2d.dispose();
		/*
		 * try { ImageIO.write(atlasImage, "png", new File("temp/atlas.png")); } catch
		 * (IOException e) { e.printStackTrace(); }
		 */

		int[] pixels = new int[atlasImage.getWidth() * atlasImage.getHeight()];
		atlasImage.getRGB(0, 0, atlasImage.getWidth(), atlasImage.getHeight(), pixels, 0, atlasImage.getWidth());

		int[] data = new int[atlasImage.getWidth() * atlasImage.getHeight()];
		for (int i = 0; i < atlasImage.getWidth() * atlasImage.getHeight(); i++) {
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
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, atlasImage.getWidth(), atlasImage.getHeight(), 0,
				GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);

		return new TextureAtlas(atlas, size, size, id);
	}

}
