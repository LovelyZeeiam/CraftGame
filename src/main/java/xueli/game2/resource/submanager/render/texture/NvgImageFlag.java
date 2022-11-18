package xueli.game2.resource.submanager.render.texture;

import static org.lwjgl.nanovg.NanoVG.*;

public enum NvgImageFlag {

	GENERATE_MIPMAPS(NVG_IMAGE_GENERATE_MIPMAPS),
	REPEAT_X(NVG_IMAGE_REPEATX),
	REPEAT_Y(NVG_IMAGE_REPEATY),
	FLIPY(NVG_IMAGE_FLIPY),
	PREMULTIPLIED(NVG_IMAGE_PREMULTIPLIED),
	NEAREST(NVG_IMAGE_NEAREST);

	int val;

	NvgImageFlag(int vgVal) {
		this.val = vgVal;
	}

}
