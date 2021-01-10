package xueli.craftgame.view;

import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgRect;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import xueli.craftgame.CraftGame;
import xueli.craftgame.WorldLogic;
import xueli.gamengine.utils.callbacks.MouseButtonCallback;
import xueli.gamengine.view.View;

public abstract class InGameBackgroundView extends View {

	private static NVGColor backgroundColor = NVGColor.create();

	static {
		NanoVG.nvgRGBA((byte) 0, (byte) 0, (byte) 0, (byte) 150, backgroundColor);

	}

	protected CraftGame game;
	protected WorldLogic logic;

	public InGameBackgroundView(WorldLogic logic, CraftGame game) {
		super("InGameView");
		this.game = game;
		this.logic = logic;

	}

	@Override
	public void size() {
		super.size();
	}

	private void tick() {
		if (MouseButtonCallback.keysOnce[GLFW.GLFW_MOUSE_BUTTON_LEFT]) {
			onLeftClick(game.getDisplay().getMouseX(), game.getDisplay().getMouseY());
		}

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

	protected void drawBox(float width, float height, NVGColor lineColor, NVGColor inboxColor, float lineWidth,
			long nvg) {
		float x = (game.getDisplay().getWidth() - width) / 2.0f;
		float y = (game.getDisplay().getHeight() - height) / 2.0f;
		drawBox(x, y, width, height, lineColor, inboxColor, lineWidth, nvg);

	}

	public abstract void onLeftClick(float x, float y);

}
