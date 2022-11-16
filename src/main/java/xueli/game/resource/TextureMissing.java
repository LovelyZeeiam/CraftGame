package xueli.game.resource;

import org.lwjgl.BufferUtils;
import xueli.game.resource.texture.TextureAtlas;
import xueli.game.resource.texture.TextureAtlasHolder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVG.nvgCreateImageMem;

public class TextureMissing {

	private static BufferedImage IMAGE_MISSING = new BufferedImage(16, 16, BufferedImage.TYPE_4BYTE_ABGR);
	private static ByteBuffer imageData;
	static {
		int i = -16777216;
		int j = -524040;

		for (int k = 0; k < 16; ++k) {
			for (int l = 0; l < 16; ++l) {
				if (k < 8 ^ l < 8) {
					IMAGE_MISSING.setRGB(l, k, -524040);
				} else {
					IMAGE_MISSING.setRGB(l, k, -16777216);
				}
			}
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(IMAGE_MISSING, "png", out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageData = BufferUtils.createByteBuffer(out.size());
		imageData.put(out.toByteArray());
		imageData.flip();

	}

	private static HashMap<Long, NVGImage> missingImageMap = new HashMap<>();

	public static NVGImage getMissingNvgImage(long nvg) {
		NVGImage image = missingImageMap.get(nvg);
		if (image == null) {
			int imageId = nvgCreateImageMem(nvg, NVG_IMAGE_NEAREST, imageData);
			image = new NVGImage(nvg, imageId);
			missingImageMap.put(nvg, image);
		}

		return image;
	}

	private static TextureAtlas missingTextureAtlas;
	private static TextureAtlasHolder missingTextureHolder;

	public static TextureAtlasHolder getMissingTexture() {
		if (missingTextureAtlas == null) {
			missingTextureAtlas = TextureAtlas.single(IMAGE_MISSING);
			missingTextureHolder = missingTextureAtlas.getHolder(TextureAtlas.SINGLE);
		}

		return missingTextureHolder;
	}

}
