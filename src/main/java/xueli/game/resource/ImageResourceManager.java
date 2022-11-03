package xueli.game.resource;

import xueli.utils.io.Files;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVG.nvgCreateImageMem;

public class ImageResourceManager extends ResourceManager<NVGImage>
		implements ResourceLoader<NVGImage>, MissingProvider<NVGImage> {

	private long currentNvg = 0;
	private int imageLoadingFlag = NVG_IMAGE_NEAREST;

	ImageResourceManager(ResourceMaster master) {
		super(master);
		setLoader(this);
		setMissingProvider(this);

	}

	public ImageResourceManager setCurrentNvg(long currentNvg) {
		this.currentNvg = currentNvg;
		return this;
	}

	public ImageResourceManager setImageLoadingFlag(int imageLoadingFlag) {
		this.imageLoadingFlag = imageLoadingFlag;
		return this;
	}

	@Override
	public NVGImage load(ResourceHolder<NVGImage> holder) throws Exception {
		ByteBuffer buffer = null;
		int imageId = -1;
		String virtualPath = holder.virtualPath;
		try {
			buffer = Files.readResourcePackedInJarAndPackedToBuffer(virtualPath);
			imageId = nvgCreateImageMem(currentNvg, imageLoadingFlag, buffer);
		} catch (IOException e) {
			throw new Exception("Couldn't load image: " + virtualPath, e);
		}
		// System.out.println(imageId);
		return new NVGImage(currentNvg, imageId);
	}

	@Override
	public void onMissing(ResourceHolder<NVGImage> holder) {
		holder.result = TextureMissing.getMissingNvgImage(currentNvg);
	}

}
