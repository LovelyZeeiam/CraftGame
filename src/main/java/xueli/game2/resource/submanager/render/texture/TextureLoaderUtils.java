package xueli.game2.resource.submanager.render.texture;

import java.awt.image.BufferedImage;

public class TextureLoaderUtils {

	public static int[] imageToLegacyData(BufferedImage image) {
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		int[] data = new int[image.getWidth() * image.getHeight()];
		for (int i = 0; i < image.getWidth() * image.getHeight(); i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		return data;
	}

}
