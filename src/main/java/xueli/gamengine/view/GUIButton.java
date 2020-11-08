package xueli.gamengine.view;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import xueli.gamengine.utils.Display;
import xueli.gamengine.utils.EvalableFloat;

import static org.lwjgl.nanovg.NanoVG.*;

public class GUIButton extends ViewWidget {

    protected static NVGPaint paint;
    private static NVGColor BLACK_COLOR = NVGColor.create();
    private static NVGColor GREY_COLOR = NVGColor.create();
    private static NVGColor ANOTHER_GREY_Color = NVGColor.create();
    private static NVGColor Darker_GREY_Color = NVGColor.create();

    static {
        paint = NVGPaint.create();

    }

    static {
        nvgRGB((byte) 0x0, (byte) 0x0, (byte) 0x0, BLACK_COLOR);
        nvgRGB((byte) 0xAAAAAA, (byte) 0xAAAAAA, (byte) 0xAAAAAA, GREY_COLOR);
        nvgRGB((byte) 0x6F6F6F, (byte) 0x6F6F6F, (byte) 0x6F6F6F, ANOTHER_GREY_Color);
        nvgRGBA((byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x415411, Darker_GREY_Color);

    }

    protected int textureID;
    private String labelString = null;
    private EvalableFloat textSize;
    private NVGColor textColor;
    private NVGColor clickedTextColor;

    public GUIButton(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height, String label,
                     EvalableFloat textSize, NVGColor textColor, NVGColor borderColor, int borderWidth,
                     NVGColor clickedTextColor) {
        super(x, y, width, height, borderColor, borderWidth);
        this.labelString = label;
        this.textSize = textSize;
        this.clickedTextColor = clickedTextColor;
        this.textColor = textColor;

        if (textSize != null) {
            textSize.eval();
        }

    }

    @Override
    public void draw(long nvg) {
        paint.clear();

        super.anim_tick();

        // 边框
        nvgBeginPath(nvg);
        nvgMoveTo(nvg, real_x, real_y);
        nvgLineTo(nvg, real_x + real_width, real_y);
        nvgLineTo(nvg, real_x + real_width, real_y + real_height);
        nvgLineTo(nvg, real_x, real_y + real_height);
        nvgLineTo(nvg, real_x, real_y);
        nvgFillColor(nvg, BLACK_COLOR);
        nvgFill(nvg);

        nvgBeginPath(nvg);
        nvgMoveTo(nvg, real_x + 3, real_y + 3);
        nvgLineTo(nvg, real_x + real_width - 3, real_y + 3);
        nvgLineTo(nvg, real_x + real_width - 3, real_y + real_height - 3);
        nvgLineTo(nvg, real_x + 3, real_y + real_height - 3);
        nvgLineTo(nvg, real_x + 3, real_y + 3);
        nvgFillColor(nvg, GREY_COLOR);
        nvgFill(nvg);

        nvgBeginPath(nvg);
        nvgMoveTo(nvg, real_x + 5, real_y + 5);
        nvgLineTo(nvg, real_x + real_width - 5, real_y + 5);
        nvgLineTo(nvg, real_x + real_width - 5, real_y + real_height - 5);
        nvgLineTo(nvg, real_x + 5, real_y + real_height - 5);
        nvgLineTo(nvg, real_x + 5, real_y + 5);
        nvgFillColor(nvg, ANOTHER_GREY_Color);
        nvgFill(nvg);

        nvgBeginPath(nvg);
        nvgMoveTo(nvg, real_x + real_width, real_y + real_height);
        nvgLineTo(nvg, real_x, real_y + real_height);
        nvgLineTo(nvg, real_x, real_y + real_height - 13);
        nvgLineTo(nvg, real_x + real_width, real_y + real_height - 13);
        nvgLineTo(nvg, real_x + real_width, real_y + real_height);
        nvgFillColor(nvg, Darker_GREY_Color);
        nvgFill(nvg);

        int cursorX = Display.currentDisplay.getMouseX();
        int cursorY = Display.currentDisplay.getMouseY();

        if (isMouseHover()) {
            super.drawBorder(nvg);
            nvgFillColor(nvg, clickedTextColor);

        } else {
            nvgFillColor(nvg, textColor);

        }

        nvgFontSize(nvg, textSize.value);
        nvgFontFace(nvg, "simhei");
        nvgTextAlign(nvg, NVG_ALIGN_CENTER);
        nvgText(nvg, real_x + real_width / 2, real_y + real_height / 2 + textSize.value / 4, labelString);

    }

    @Override
    public void size() {
        textSize.eval();
        super.size();
    }

}
