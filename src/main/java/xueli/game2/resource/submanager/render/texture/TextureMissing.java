package xueli.game2.resource.submanager.render.texture;

import org.lwjgl.utils.vector.Vector2f;
import xueli.game2.resource.submanager.render.texture.atlas.AtlasResourceHolder;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class TextureMissing {

	public static final BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_4BYTE_ABGR);

	static {
		int rgb1 = new Color(0, 0, 0).getRGB();
		int rgb2 = new Color(248, 0, 248).getRGB();

		for (int y = 0; y < 16; ++y) {
			for (int x = 0; x < 16; ++x) {
				if ((x < 8) ^ (y < 8)) {
					image.setRGB(x, y, rgb1);
				} else {
					image.setRGB(x, y, rgb2);
				}
			}
		}

	}

	private static HashMap<TextureType, Integer> textures = new HashMap<>();

	public static int get(TextureType type) {
		return textures.computeIfAbsent(type, t -> {
			try {
				return t.getLoader().registerTexture(image);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	private static AtlasResourceHolder atlasHolder;

	public static AtlasResourceHolder getAtlasHolder() {
		if(atlasHolder != null)
			return atlasHolder;
		int id = get(TextureType.LEGACY);
		atlasHolder = new AtlasResourceHolder(new Vector2f(0,0), new Vector2f(1,1), id);
		return atlasHolder;
	}

	static {
		TextureMissing.getAtlasHolder();
		TextureMissing.get(TextureType.LEGACY);
		TextureMissing.get(TextureType.NVG);
	}

}
