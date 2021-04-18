package xueli.game.renderer;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_DEBUG;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.nanovg.NanoVGGL3.nvgDelete;

import java.util.logging.Logger;

import org.lwjgl.nanovg.NVGPaint;

import xueli.game.Game;
import xueli.game.lang.LangManager;

public abstract class NVGRenderer implements Renderer {

	protected static final NVGPaint paint = NVGPaint.create();

	protected long nvg;
	protected Game game;
	protected LangManager lang;

	public NVGRenderer() {
		this.game = Game.INSTANCE_GAME;

		this.lang = new LangManager(Game.DEFAULT_RES_DIRECTORY_STRING);
		this.lang.loadLang();

		this.nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (this.nvg == 0) {
			Logger.getLogger(getClass().getName()).severe("[GUI] Can't create NVG!");
		}

	}

	@Override
	public void render() {
		nvgBeginFrame(nvg, game.getWidth(), game.getHeight(), game.getWidth() / game.getHeight());
		stroke();
		nvgEndFrame(nvg);

	}

	public abstract void stroke();

	protected float measureTextWidth(float size, String text) {
		return nvgText(nvg, 0, -10000000, text);
	}

	@Override
	public void size() {

	}

	@Override
	public void release() {
		nvgDelete(nvg);

	}

}
