package xueLi.craftGame.gui;

import xueLi.craftGame.events.KeyEvent;
import xueLi.craftGame.events.MouseButtonEvent;

import static org.lwjgl.nanovg.NanoVG.*;

import static xueLi.craftGame.gui.GUIRenderer.nvg;

import org.lwjgl.nanovg.NVGColor;

public class GuiTextView extends GUIWidget {

	private String label;
	private String fontName;
	private int fontSize;
	private int align;
	private NVGColor color;
	
	public GuiTextView(String label,String fontName,int fontSize,NVGColor color,int align,float x, float y, float width, float height) {
		super(x, y, null, width, height);
		this.label = label;
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.align = align;
		this.color = color;
		
	}

	@Override
	public void render() {
		nvgFontSize(nvg, fontSize);
		nvgFontFace(nvg, fontName);
		nvgTextAlign(nvg, align);
		nvgFillColor(nvg, color);
		nvgText(nvg, x, y, label);
		
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
		

	}

	@Override
	public void onMouseButtonEvent(MouseButtonEvent event) {
		

	}

}
