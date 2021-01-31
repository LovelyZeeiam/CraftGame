package xueli.craftgame.view;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;
import static org.lwjgl.nanovg.NanoVG.nvgTextBox;

import xueli.craftgame.block.Tile;
import xueli.craftgame.world.WorldLogic;
import xueli.gamengine.view.GuiColor;

public class BlockMessageView extends InGameView {

    private static final int width = 741, height = 495;
    private static final int padding = 20;
    private static final int title_font_size = 30, content_font_size = 18;

    private final Tile tile;
    private final String message_title;

    private final int texture_background;

    public BlockMessageView(Tile tile, WorldLogic logic) {
        super(logic);
        this.tile = tile;

        this.texture_background = logic.getNvgTextures().get("ingame.gui.block_message");

        this.message_title = game.getLangManager().getStringFromLangMap("#hud.ingame.block_message.title") + tile.getData().getBlockName();

    }

    @Override
    public void onClick(float x, float y, int button) {

    }

    private float scroll_offset = 0;

    @Override
    public void onScroll(float x, float y, float scroll) {
        scroll_offset += scroll * 30;

    }

    @Override
    public void draw(long nvg) {
        super.draw(nvg);

        float realSizeWidth = width * display.getScale();
        float realSizeHeight = height * display.getScale();

        float corner_x = (display.getWidth() - realSizeWidth) / 2.0f;
        float corner_y = (display.getHeight() - realSizeHeight) / 2.0f;

        // 背景
        nvgImagePattern(nvg, corner_x, corner_y, realSizeWidth, realSizeHeight, 0, texture_background, 1, getPaint());
        nvgBeginPath(nvg);
        nvgRoundedRect(nvg, corner_x, corner_y, realSizeWidth, realSizeHeight, 0);
        nvgFillPaint(nvg, getPaint());
        nvgFill(nvg);

        // 标题
        nvgFontSize(nvg, title_font_size * display.getScale());
        nvgFontFace(nvg, "game");
        nvgTextAlign(nvg, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE);
        nvgFillColor(nvg, GuiColor.BLACK);
        nvgText(nvg, display.getWidth() / 2.0f, corner_y + padding * display.getScale(), this.message_title);

        float content_x = corner_x + padding * display.getScale();
        float content_y = corner_y + padding * display.getScale() + title_font_size * display.getScale();

        //裁剪
        //nvgSave(nvg);
        //nvgScissor(nvg, content_x, content_y, realSizeWidth, realSizeHeight);

        nvgFontSize(nvg, content_font_size * display.getScale());
        nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgTextBox(nvg, content_x, content_y + scroll_offset, realSizeWidth - padding * 2* display.getScale(), tile.getParams().message);

        // 取消裁剪
        //nvgRestore(nvg);

    }

}
