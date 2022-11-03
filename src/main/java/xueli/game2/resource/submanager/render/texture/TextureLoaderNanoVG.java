package xueli.game2.resource.submanager.render.texture;

import xueli.game2.renderer.ui.NanoVGContext;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVGGL3.nvglCreateImageFromHandle;

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
