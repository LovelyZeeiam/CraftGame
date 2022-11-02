package xueli.craftgame.renderer.view;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgText;

import org.lwjgl.nanovg.NVGPaint;

import xueli.craftgame.renderer.GameViewRenderer;
import xueli.game.input.InputHolder;
import xueli.game.resource.ImageResourceManager;
import xueli.game2.display.Display;

public abstract class IngameView implements INVGRenderer {

	private GameViewRenderer master;
	protected InputHolder inputHolder;

	protected NVGPaint paint = NVGPaint.create();

	protected ImageResourceManager imageResourceManager;

	public IngameView(GameViewRenderer master) {
		this.master = master;
		this.inputHolder = master.getInputHolder();

//		this.imageResourceManager = master.getContext().getResourceMaster().getImageResourceManager();

	}

	@Override
	public void render(long nvg) {
//		Display display = master.getContext().getDisplay();
//		nvgBeginFrame(nvg, display.getWidth(), display.getHeight(), (float) display.getWidth() / display.getHeight());
//		stroke(nvg);
//		nvgEndFrame(nvg);

	}

	protected abstract void stroke(long nvg);

	protected float measureTextWidth(long nvg, float size, String text) {
		nvgFontSize(nvg, size);
		return nvgText(nvg, 0, -10000000, text);
	}

	@Override
	public void release(long nvg) {

	}

	public GameViewRenderer getMaster() {
		return master;
	}

}
