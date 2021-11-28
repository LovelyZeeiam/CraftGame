package xueli.game.renderer;

import org.lwjgl.nanovg.NVGPaint;
import xueli.game.Game;
import xueli.game.lang.FiledLangManager;
import xueli.utils.logger.MyLogger;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

public abstract class NVGRenderer implements Renderer {

	protected static final NVGPaint paint = NVGPaint.create();

	protected long nvg;
	protected Game game;
	protected FiledLangManager lang;

	public NVGRenderer() {
		this.game = Game.INSTANCE_GAME;

		this.lang = new FiledLangManager(Game.DEFAULT_RES_DIRECTORY_STRING);
		this.lang.loadLang();

		MyLogger.getInstance().pushState("nanovg");

		this.nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (this.nvg == 0) {
			MyLogger.getInstance().error("Can't create NVG!");
			System.exit(-1);
		}

		MyLogger.getInstance().popState();

	}

	@Override
	public void render() {
		nvgBeginFrame(nvg, game.getWidth(), game.getHeight(), game.getWidth() / game.getHeight());
		stroke();
		nvgEndFrame(nvg);

	}

	public abstract void stroke();

	protected float measureTextWidth(float size, String text) {
		nvgFontSize(nvg, size);
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
