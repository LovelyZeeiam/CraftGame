package xueli.craftgame.block.rendercontrol.frameface;

import xueli.gamengine.utils.renderer.FrameViewFace;
import xueli.gamengine.view.GuiColor;

import static org.lwjgl.nanovg.NanoVG.*;

public class FrameFaceSign extends FrameViewFace {

	private String text;

	public FrameFaceSign(String text, long nvg, float[] data) {
		super(nvg, 400, 300, data);
		this.text = text;

	}

	@Override
	public void renderToFrameBuffer() {
		nvgFontFace(nvg, "game");
		nvgFillColor(nvg, GuiColor.WHITE);
		nvgTextAlign(nvg, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE);
		nvgText(nvg, 200, 150, text);

	}

}
