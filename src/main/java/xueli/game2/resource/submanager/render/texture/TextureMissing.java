package xueli.game2.resource.submanager.render.texture;

import java.awt.Color;
import java.awt.image.BufferedImage;

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

	public static Texture get(AbstractTextureLoader loader) {
		try {
			return loader.registerTexture(image);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
