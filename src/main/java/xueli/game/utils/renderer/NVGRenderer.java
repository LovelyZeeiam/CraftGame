package xueli.game.utils.renderer;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_DEBUG;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.nanovg.NanoVGGL3.nvgDelete;

import xueli.game.Game;
import xueli.utils.io.Log;

public abstract class NVGRenderer implements Renderer {

	protected long nvg;
	protected Game game;

	public NVGRenderer() {
		this.game = Game.INSTANCE_GAME;

		this.nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (this.nvg == 0) {
			Log.logger.severe("[GUI] Can't create NVG!");
		}

	}

	@Override
	public void render() {
		nvgBeginFrame(nvg, game.getWidth(), game.getHeight(), game.getWidth() / game.getHeight());
		stroke();
		nvgEndFrame(nvg);

	}

	public abstract void stroke();

	@Override
	public void size(int w, int h) {

	}

	@Override
	public void release() {
		nvgDelete(nvg);

	}

}
