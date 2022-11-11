package xueli.game.ui;

import xueli.utils.io.Files;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.nanovg.NanoVG.*;

public class ImageManager {

	private UIRenderer renderer;
	private HashMap<String, Integer> imagesHashMap = new HashMap<>();

	public ImageManager(UIRenderer renderer) {
		this.renderer = renderer;

	}

	public int getImage(String pathInJar) {
		Integer imageInteger = imagesHashMap.get(pathInJar);

		if (imageInteger == null) {
			ByteBuffer buffer = null;
			try {
				buffer = Files.readResourcePackedInJarAndPackedToBuffer(pathInJar);
			} catch (IOException e) {
				e.printStackTrace();
				return 0;
			}

			imageInteger = nvgCreateImageMem(renderer.nvg, NVG_IMAGE_NEAREST, buffer);
			imagesHashMap.put(pathInJar, imageInteger);

		}

		return imageInteger;
	}

	public void release() {
		imagesHashMap.values().forEach(i -> {
			nvgDeleteImage(renderer.nvg, i);
		});
	}

}
