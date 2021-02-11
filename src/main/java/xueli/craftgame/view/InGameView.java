package xueli.craftgame.view;

import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgCircle;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRect;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

import xueli.craftgame.CraftGame;
import xueli.craftgame.WorldLogic;
import xueli.gamengine.utils.Display;
import xueli.gamengine.view.View;

public abstract class InGameView extends View {

	private static NVGColor backgroundColor = NVGColor.create();
	private static NVGPaint paint = NVGPaint.create();

	static {
		NanoVG.nvgRGBA((byte) 0, (byte) 0, (byte) 0, (byte) 150, backgroundColor);

	}

	protected CraftGame game;
	protected WorldLogic logic;
	protected Display display;

	public InGameView(WorldLogic logic) {
		super("InGameView");
		this.game = logic.getCg();
		this.logic = logic;
		this.display = this.game.getDisplay();

	}

	@Override
	public void size() {
		super.size();
	}

	private void tick() {

	}

	@Override
	public void draw(long nvg) {
		tick();

		nvgBeginPath(nvg);
		nvgRect(nvg, 0, 0, game.getDisplay().getWidth(), game.getDisplay().getHeight());
		nvgFillColor(nvg, backgroundColor);
		nvgFill(nvg);

	}

	protected void drawBox(float x, float y, float width, float height, NVGColor lineColor, NVGColor inboxColor,
			float lineWidth, long nvg) {
		nvgBeginPath(nvg);
		nvgRect(nvg, x - lineWidth, y - lineWidth, width + lineWidth * 2, height + lineWidth * 2);
		nvgFillColor(nvg, lineColor);
		nvgFill(nvg);

		nvgBeginPath(nvg);
		nvgRect(nvg, x, y, width, height);
		nvgFillColor(nvg, inboxColor);
		nvgFill(nvg);

	}

	protected void drawImage(float x, float y, float width, float height, int imageID, long nvg) {
		nvgImagePattern(nvg, x, y, width, height, 0, imageID, 1, paint);
		nvgBeginPath(nvg);
		nvgRect(nvg, x, y, width, height);
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);
	}

	protected void drawBox(float width, float height, NVGColor lineColor, NVGColor inboxColor, float lineWidth,
			long nvg) {
		float x = (game.getDisplay().getWidth() - width) / 2.0f;
		float y = (game.getDisplay().getHeight() - height) / 2.0f;
		drawBox(x, y, width, height, lineColor, inboxColor, lineWidth, nvg);

	}

	protected void drawPoint(float x, float y, NVGColor color, long nvg) {
		nvgBeginPath(nvg);
		nvgFillColor(nvg, color);
		nvgCircle(nvg, x, y, 1.0f);
		nvgFill(nvg);
	}

	protected static NVGPaint getPaint() {
		return paint;
	}

	public abstract void onClick(float x, float y, int button);

	public abstract void onScroll(float x, float y, float scroll);

}
