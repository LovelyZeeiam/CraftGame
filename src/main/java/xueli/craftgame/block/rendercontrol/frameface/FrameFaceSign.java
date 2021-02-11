package xueli.craftgame.block.rendercontrol.frameface;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;

import xueli.gamengine.utils.renderer.FrameViewFace;
import xueli.gamengine.view.GuiColor;

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
