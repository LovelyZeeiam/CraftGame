package xueLi.gamengine.gui;

import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRect;

import org.lwjgl.nanovg.NVGColor;

import xueLi.gamengine.utils.Display;
import xueLi.gamengine.utils.EvalableFloat;

public class GUIButton extends GUIImageView {

	public GUIButton(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height, int textureID,
			NVGColor borderColor, int borderWidth) {
		super(x, y, width, height, textureID, borderColor, borderWidth);
		
	}

	@Override
	public void draw(long nvg) {
		paint.clear();

		super.anim_tick();
		
		int cursorX = Display.currentDisplay.getMouseX();
		int cursorY = Display.currentDisplay.getMouseY();
		if(cursorX > real_x & cursorX < real_x + real_width & cursorY > real_y & cursorY < real_y + real_height) {
			super.drawBorder(nvg);
			
		}
		
		nvgImagePattern(nvg, real_x, real_y, real_width, real_height, 0, textureID, 1, paint);
		nvgBeginPath(nvg);
		nvgRoundedRect(nvg, real_x, real_y, real_width, real_height, 0);
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);
		
	}

}
