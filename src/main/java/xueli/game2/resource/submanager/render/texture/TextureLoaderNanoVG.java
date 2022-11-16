package xueli.game2.resource.submanager.render.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVGGL3.nvglCreateImageFromHandle;

public class TextureLoaderNanoVG extends TextureLoaderLegacy {

	private final long nvg;

	private TextureLoaderNanoVG(long nvg) {
		this.nvg = nvg;
	}

	@Override
	int registerTexture(BufferedImage image) throws IOException {
		int legacyId = super.registerTexture(image);
		return nvglCreateImageFromHandle(nvg, legacyId, image.getWidth(), image.getHeight(), NVG_IMAGE_NEAREST);
	}

	public static TextureLoaderNanoVG getInstance(long nvg) {
		return new TextureLoaderNanoVG(nvg);
	}

}
