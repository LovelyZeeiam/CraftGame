package xueLi.craftGame.gui;

import static org.lwjgl.nanovg.NanoVG.*;

import static xueLi.craftGame.gui.GUIRenderer.nvg;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.util.vector.Vector4b;
import xueLi.craftGame.inputListener.KeyEvent;
import xueLi.craftGame.inputListener.MouseButtonEvent;
import xueLi.craftGame.utils.GLHelper;

public class GuiTextView extends GUIWidget {

	private String label;
	private String fontName;
	private int fontSize;
	private int align;
	private NVGColor color;

	public GuiTextView(String label, String fontName, int fontSize, Vector4b color, int align, float x, float y,
			float width, float height) {
		super(x, y, null, width, height);
		this.label = label;
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.align = align;
		this.color = GLHelper.getNvgColorRGBA(color);

	}

	//private static float box_width = 2.0f;

	@Override
	public void render() {
		nvgFontSize(nvg, fontSize);
		nvgFontFace(nvg, fontName);
		nvgTextAlign(nvg, align);
		nvgFillColor(nvg, color);
		nvgText(nvg, x, y, label);
		
		/*
		 * nvgBeginPath(nvg);
		 * 
		 * nvgFillColor(nvg, color); // 上边框 nvgRoundedRect(nvg, x - box_width, y -
		 * box_width, box_width * 2 + width, box_width, 0); // 左边框 nvgRoundedRect(nvg, x
		 * - box_width, y, box_width, height, 0); // 下边框 nvgRoundedRect(nvg, x -
		 * box_width, y + height, box_width * 2 + width, box_width, 0); // 右边框
		 * nvgRoundedRect(nvg, x + width, y, box_width, height, 0);
		 * 
		 * nvgFill(nvg);
		 */
		
	}

	@Override
	public void onKeyEvent(KeyEvent event) {

	}

	@Override
	public void onMouseButtonEvent(MouseButtonEvent event) {

	}

}
