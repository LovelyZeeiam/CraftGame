package xueli.game2.renderer.ui;

import org.lwjgl.nanovg.NVGPaint;

import java.io.Closeable;
import java.io.IOException;

import static org.lwjgl.nanovg.NanoVGGL3.*;

/**
 * Maybe later when we want to draw something on the sign this would not be working
 */
@Deprecated
public class NanoVGContext implements Closeable {

	public static NanoVGContext INSTANCE;

	static {
		INSTANCE = new NanoVGContext();
	}

	public static void init() {}

	public static void release() {
		if (INSTANCE != null) {
			try {
				INSTANCE.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private long nvg;
	private NVGPaint paint = NVGPaint.create();

	public NanoVGContext() {
		this.nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (this.nvg == 0) {
			throw new RuntimeException("Couldn't init NanoVG!");
		}
	}
	
	public long getNvg() {
		return nvg;
	}

	public NVGPaint getPaint() {
		return paint;
	}
	
	@Override
	public void close() throws IOException {
		nvgDelete(nvg);
	}

}
