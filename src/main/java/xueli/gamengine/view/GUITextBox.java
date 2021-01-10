package xueli.gamengine.view;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgMoveTo;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgRestore;
import static org.lwjgl.nanovg.NanoVG.nvgSave;
import static org.lwjgl.nanovg.NanoVG.nvgScissor;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;
import static org.lwjgl.nanovg.NanoVG.nvgTextGlyphPositions;
import static org.lwjgl.nanovg.NanoVG.nvgTextMetrics;

import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGGlyphPosition;

import xueli.gamengine.utils.EvalableFloat;
import xueli.gamengine.view.text.KeyType;

@Deprecated
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

	public GUITextBox(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height,
			NVGColor backgroundColor, NVGColor borderColor, int border_width, NVGColor textColor,
			EvalableFloat textSize, String hint) {
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
		nvgRect(nvg, x.getValue(), y.getValue(), width.getValue(), this.height.getValue());
		nvgFillColor(nvg, GuiColor.BLACK);
		nvgFill(nvg);

		nvgBeginPath(nvg);
		nvgRect(nvg, x.getValue() + in_border, y.getValue() + in_border, width.getValue() - 2 * in_border,
				this.height.getValue() - 2 * in_border);
		nvgFillColor(nvg, backgroundColor);
		nvgFill(nvg);

		if (this.isSelectedLastTime || isMouseHover()) {
			super.drawBorder(nvg);

		}

		nvgSave(nvg);
		nvgScissor(nvg, x.getValue(), y.getValue(), width.getValue(), this.height.getValue());

		nvgFontSize(nvg, textSize.getValue());
		nvgFontFace(nvg, "simhei");
		nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE);
		nvgFillColor(nvg, textColor);
		nvgText(nvg, x.getValue() + in_border - offset_x, y.getValue() + this.height.getValue() / 2 + font_offset,
				text);

		nvgTextMetrics(nvg, null, null, line_height);

		if (this.isSelectedLastTime) {
			// 文本框光标
			nvgBeginPath(nvg);
			nvgMoveTo(nvg, lettersPos[pointer], y.getValue() + in_border);
			nvgRect(nvg, lettersPos[pointer] - offset_x, y.getValue() + in_border, pointer_width,
					this.height.getValue() - 2 * in_border);
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

		nvgFontSize(nvg, textSize.getValue());
		nvgFontFace(nvg, "simhei");
		nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE);
		nvgTextGlyphPositions(nvg, x.getValue() + in_border - offset_x, y.getValue(), text, letterPosBuffer);

		List<NVGGlyphPosition> nvgGlyphPositions = letterPosBuffer.stream().collect(Collectors.toList());
		lettersPos[0] = x.getValue() + in_border;
		for (int i = 0; i < nvgGlyphPositions.size(); i++) {
			lettersPos[i + 1] = nvgGlyphPositions.get(i).maxx();

		}

		// TODO: 文本框内的文字的移动 然而不会逻辑的说,,, 以后再说吧

	}

}
