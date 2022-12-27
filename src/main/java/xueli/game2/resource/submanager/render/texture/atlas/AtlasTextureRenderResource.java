package xueli.game2.resource.submanager.render.texture.atlas;

import org.lwjgl.opengl.GL11;
import org.lwjgl.utils.vector.Vector2f;
import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.manager.SubResourceManager;
import xueli.game2.resource.submanager.render.texture.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class AtlasTextureRenderResource extends SubResourceManager {

	private final ArrayList<RegisterData> registerData = new ArrayList<>();
	private final ArrayList<Integer> registeredTexture = new ArrayList<>();
	private final HashMap<ResourceLocation, HashMap<String, AtlasResourceHolder>> atlasHolders = new HashMap<>();

	public AtlasTextureRenderResource(TextureRenderResource superiorManager) {
		super(superiorManager);
	}

	private record RegisterData(ResourceLocation path, Predicate<String> selector) {}

	public void findAndRegister(ResourceLocation path, Predicate<String> selector) {
		registerData.add(new RegisterData(path, selector));

		List<Resource> resources = null;
		try {
			resources = this.findResources(path, selector);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		if(resources.size() == 0) {
			System.err.println("Can't find atlas in: " + path);
			return;
		}

		int[] maxWidth = {0}, maxHeight = {0};
		int count = 0;

		List<BufferedImage> images = resources.stream()
				.filter(res -> selector.test(res.getName()))
				.map(res -> {
					try {
						InputStream in = res.openInputStream();
						BufferedImage image = ImageIO.read(in);

						maxWidth[0] = Math.max(maxWidth[0], image.getWidth());
						maxHeight[0] = Math.max(maxHeight[0], image.getHeight());

						return image;
					} catch (IOException e) {
						e.printStackTrace();
						return TextureMissing.image;
					}
				})
				.toList();
		count = images.size();

		int atlasSize = (int) Math.ceil(Math.sqrt(count));
		int atlasImageWidth = atlasSize * maxWidth[0];
		int atlasImageHeight = atlasSize * maxHeight[0];

		HashMap<String, AtlasResourceHolder> list = atlasHolders.computeIfAbsent(path, key -> new HashMap<>());
		BufferedImage atlasImage = new BufferedImage(atlasImageWidth, atlasImageHeight, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = atlasImage.createGraphics();

		AbstractTextureLoader loader = new TextureLoaderLegacy();
		int textureId = loader.createTexture();
		registeredTexture.add(textureId);

		for (int i = 0; i < images.size(); i++) {
			BufferedImage image = images.get(i);
			int x = i % atlasSize;
			int y = i / atlasSize;

			Vector2f leftTop = new Vector2f((float) x / atlasSize, (float) y / atlasSize);
			Vector2f rightBottom = new Vector2f((float) (x * maxWidth[0] + image.getWidth()) / atlasImageWidth, (float) (y * maxHeight[0] + image.getHeight()) / atlasImageHeight);
			list.put(resources.get(i).getName(), new AtlasResourceHolder(leftTop, rightBottom, textureId));

			g2d.drawImage(image, x * maxWidth[0], y * maxHeight[0], null);

		}

		g2d.dispose();

		int[] imageData = TextureLoaderUtils.imageToLegacyData(atlasImage);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, atlasImageWidth, atlasImageHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

	}

	public Map<String, AtlasResourceHolder> getAllHolders(ResourceLocation path) {
		HashMap<String, AtlasResourceHolder> map = atlasHolders.get(path);
		return map;
	}

	public AtlasResourceHolder getHolder(ResourceLocation path, String name) {
		HashMap<String, AtlasResourceHolder> map = atlasHolders.get(path);
		if(map == null)
			return TextureMissing.getAtlasHolder();
		AtlasResourceHolder holder = map.get(name);
		if(holder == null)
			return TextureMissing.getAtlasHolder();
		return holder;
	}

	@Override
	public void reload() {
		this.closeThis();

		for (RegisterData data : registerData) {
			this.findAndRegister(data.path(), data.selector());
		}

		super.reload();
	}

	private void closeThis() {
		registeredTexture.forEach(GL11::glDeleteTextures);
		registeredTexture.clear();
		atlasHolders.clear();
	}

	@Override
	public void close() throws IOException {
		closeThis();
		super.close();
	}

}
