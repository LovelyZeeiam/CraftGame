package xueli.game2.resource.submanager.render.texture;

import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_FLIPY;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_GENERATE_MIPMAPS;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_PREMULTIPLIED;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_REPEATX;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_REPEATY;

public enum NvgImageFlag {

	GENERATE_MIPMAPS(NVG_IMAGE_GENERATE_MIPMAPS), REPEAT_X(NVG_IMAGE_REPEATX), REPEAT_Y(NVG_IMAGE_REPEATY),
	FLIPY(NVG_IMAGE_FLIPY), PREMULTIPLIED(NVG_IMAGE_PREMULTIPLIED), NEAREST(NVG_IMAGE_NEAREST);

	int val;

	NvgImageFlag(int vgVal) {
		this.val = vgVal;
	}

}
