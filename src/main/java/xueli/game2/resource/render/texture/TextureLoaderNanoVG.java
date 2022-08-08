package xueli.game2.resource.render.texture;

import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVGGL3.nvglCreateImageFromHandle;

import java.awt.image.BufferedImage;
import java.io.IOException;

import xueli.game2.renderer.ui.NanoVGContext;

public class TextureLoaderNanoVG extends TextureLoaderLegacy {

	@Override
	int registerTexture(BufferedImage image) throws IOException {
		int legacyId = super.registerTexture(image);
		long nvg = NanoVGContext.INSTANCE.getNvg();
		int handle = nvglCreateImageFromHandle(nvg, legacyId, image.getWidth(), image.getHeight(), NVG_IMAGE_NEAREST);
		return handle;
	}

	@Override
	public void applyTexture(int id) {
	}

}
