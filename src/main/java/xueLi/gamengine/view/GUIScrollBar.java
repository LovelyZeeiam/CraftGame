package xueLi.gamengine.view;

import org.lwjgl.nanovg.NVGColor;

import xueLi.gamengine.utils.Display;
import xueLi.gamengine.utils.EvalableFloat;

import static org.lwjgl.nanovg.NanoVG.*;

import org.lwjgl.glfw.GLFW;

public class GUIScrollBar extends ViewWidget implements ViewWidget.OnClickListener {

	private static final int beside_width = 2;
	private static final int scroll_bar_height_plus = 10;
	private static final int scroll_bar_width = 18;

	private float value; // a float from 0.0 to 1.0
	private NVGColor background_color, scrollbar_color;

	public GUIScrollBar(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height, float value,
			NVGColor background_color, NVGColor scrollbar_color, NVGColor outlineColor, int outlineWidth) {
		super(x, y, width, height, outlineColor, outlineWidth);

		this.background_color = background_color;
		this.scrollbar_color = scrollbar_color;
		this.value = value;

		this.onClickListener = this;

	}
	
	private boolean mouseDrag = false;

	@Override
	public void draw(long nvg) {
		float scale = Display.currentDisplay.getScale();

		// 边框
		nvgBeginPath(nvg);
		nvgRect(nvg, real_x, real_y, real_width, real_height);
		nvgFillColor(nvg, GuiColor.BLACK);
		nvgFill(nvg);

		// 背景
		nvgBeginPath(nvg);
		nvgRect(nvg, real_x + beside_width * scale, real_y + beside_width * scale,
				real_width - 2 * beside_width * scale, real_height - 2 * beside_width * scale);
		nvgFillColor(nvg, background_color);
		nvgFill(nvg);

		boolean isMouseHover = isMouseHover();

		if (isMouseHover) {
			// 选中边框
			super.drawBorder(nvg);

		}

		// 滑动块块的中心
		float center_y = real_y + real_height / 2;
		float center_x = real_x + scroll_bar_width * scale / 2 + (real_width - scroll_bar_width * scale) * value;

		if(mouseDrag) {
			float leftLimitX = real_x + scroll_bar_width * scale / 2;
			float rightLimitX = real_x + scroll_bar_width * scale / 2 + (real_width - scroll_bar_width * scale);
			
			float value = (Display.currentDisplay.getMouseX() - leftLimitX) / (rightLimitX - leftLimitX);
			this.value = Math.max(0.0f, value);
			this.value = Math.min(this.value, 1.0f);
			
		}
		
		float height = real_height + scroll_bar_height_plus * scale;
		float width = scroll_bar_width * scale;
		float x = center_x - scroll_bar_width * scale / 2;
		float y = center_y - height / 2;

		// 滑动块块的选中边框
		if (mouseDrag || isMouseHover) {
			nvgBeginPath(nvg);
			nvgRect(nvg, x - borderWidth, y - borderWidth, width + 2 * borderWidth, height + 2 * borderWidth);
			nvgFillColor(nvg, borderColor);
			nvgFill(nvg);
		}

		// 滑动块块的边框
		nvgBeginPath(nvg);
		nvgRect(nvg, x, y, width, height);
		nvgFillColor(nvg, GuiColor.BLACK);
		nvgFill(nvg);

		// 真正的滑动块块
		nvgBeginPath(nvg);
		nvgRect(nvg, x + borderWidth, y + borderWidth, width - 2 * borderWidth, height - 2 * borderWidth);
		nvgFillColor(nvg, scrollbar_color);
		nvgFill(nvg);

	}

	@Override
	public void onClick(int button,int action,double offsetX,double offsetY) {
		mouseDrag = action == GLFW.GLFW_PRESS;
		
	}

}
