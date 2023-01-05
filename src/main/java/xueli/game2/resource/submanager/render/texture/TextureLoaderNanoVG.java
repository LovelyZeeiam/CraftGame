package xueli.game2.resource.submanager.render.texture;

import static org.lwjgl.nanovg.NanoVGGL3.nvglCreateImageFromHandle;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class TextureLoaderNanoVG extends TextureLoaderLegacy {

	private final long nvg;
	private final int flag;

	public TextureLoaderNanoVG(long nvg) {
		this.nvg = nvg;
		this.flag = NvgImageFlag.NEAREST.val;
	}

	TextureLoaderNanoVG(long nvg, NvgImageFlag... flags) {
		this.nvg = nvg;

		int f = 0;
		for (NvgImageFlag flag : flags) {
			f |= flag.val;
		}
		this.flag = f;

	}

	@Override
	public int registerTexture(BufferedImage image) throws IOException {
		int legacyId = super.registerTexture(image);
		return nvglCreateImageFromHandle(nvg, legacyId, image.getWidth(), image.getHeight(), flag);
	}

	public static TextureLoaderNanoVG getInstance(long nvg) {
		return new TextureLoaderNanoVG(nvg);
	}

}
