package xueli.gamengine.view;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGGlyphPosition;
import xueli.gamengine.utils.EvalableFloat;
import xueli.gamengine.view.text.KeyType;

import java.util.List;
import java.util.stream.Collectors;

import static org.lwjgl.nanovg.NanoVG.*;

public class GUITextBox extends ViewWidget {

    private static final int in_border = 2;
    private static final int font_offset = 1;
    private static final int pointer_width = 1;

    private long nvg;

    private String hint;
    private NVGColor backgroundColor;
    private NVGColor textColor;
    private EvalableFloat textSize;

    private StringBuffer text = new StringBuffer();
    private float[] lettersPos = new float[1];

    private float[] line_height = new float[1];
    private int pointer = 0;
    private float offset_x = 0;

    public GUITextBox(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height, NVGColor backgroundColor, NVGColor borderColor, int border_width, NVGColor textColor, EvalableFloat textSize, String hint) {
        super(x, y, width, height, borderColor, border_width);
        this.hint = hint;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.textSize = textSize;

    }

    @Override
    public void size() {
        super.size();

        calcTextPos();

    }

    @Override
    public void draw(long nvg) {
        this.nvg = nvg;

        nvgBeginPath(nvg);
        nvgRect(nvg, real_x, real_y, real_width, real_height);
        nvgFillColor(nvg, GuiColor.BLACK);
        nvgFill(nvg);

        nvgBeginPath(nvg);
        nvgRect(nvg, real_x + in_border, real_y + in_border, real_width - 2 * in_border, real_height - 2 * in_border);
        nvgFillColor(nvg, backgroundColor);
        nvgFill(nvg);

        if (this.isSelectedLastTime || isMouseHover()) {
            super.drawBorder(nvg);

        }

        nvgSave(nvg);
        nvgScissor(nvg, real_x, real_y, real_width, real_height);

        nvgFontSize(nvg, textSize.value);
        nvgFontFace(nvg, "simhei");
        nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE);
        nvgFillColor(nvg, textColor);
        nvgText(nvg, real_x + in_border - offset_x, real_y + real_height / 2 + font_offset, text);

        nvgTextMetrics(nvg, null, null, line_height);

        if (this.isSelectedLastTime) {
            // 文本框光标
            nvgBeginPath(nvg);
            nvgMoveTo(nvg, lettersPos[pointer], real_y + in_border);
            nvgRect(nvg, lettersPos[pointer] - offset_x, real_y + in_border, pointer_width, real_height - 2 * in_border);
            nvgFillColor(nvg, GuiColor.YELLOW);
            nvgFill(nvg);

        }

        nvgRestore(nvg);


    }

    @Override
    public void keyDown(int key, KeyType type) {
        if (type == KeyType.READABLE) {
            // codepoint
            String chars = new String(Character.toChars(key));
            text.insert(pointer, chars);

            calcTextPos();
            pointer++;

        } else if (type == KeyType.CONTROL) {
            switch (key) {
                case 259:
                    if (pointer > 0) {
                        text.deleteCharAt(pointer - 1);
                        pointer--;

                    }
                    break;
            }
            calcTextPos();

        }


    }

    private void calcTextPos() {
        if (nvg == 0)
            return;

        lettersPos = new float[text.length() + 1];
        NVGGlyphPosition.Buffer letterPosBuffer = NVGGlyphPosition.malloc(text.length());

        nvgFontSize(nvg, textSize.value);
        nvgFontFace(nvg, "simhei");
        nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE);
        nvgTextGlyphPositions(nvg, real_x + in_border - offset_x, real_y, text, letterPosBuffer);

        List<NVGGlyphPosition> nvgGlyphPositions = letterPosBuffer.stream().collect(Collectors.toList());
        lettersPos[0] = real_x + in_border;
        for (int i = 0; i < nvgGlyphPositions.size(); i++) {
            lettersPos[i + 1] = nvgGlyphPositions.get(i).maxx();

        }

        // TODO: 文本框内的文字的移动 然而不会逻辑的说,,, 以后再说吧


    }

}
