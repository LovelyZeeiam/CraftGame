package xueLi.gamengine.gui;

import org.lwjgl.nanovg.NVGColor;

import xueLi.gamengine.utils.EvalableFloat;
import xueLi.gamengine.utils.Logger;

import static org.lwjgl.nanovg.NanoVG.*;

import java.util.HashMap;

public abstract class GUIWidget implements AutoCloseable {

	EvalableFloat x, y, width, height;
	public float real_x, real_y, real_width, real_height;

	private boolean hasBorder = false;
	private NVGColor borderColor;
	private int borderWidth;

	public HashMap<String, IAnimation> animations;
	private IAnimation currentAnimation = null;

	public boolean mouseClicked = false;

	public GUIWidget(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		anim_tick();

	}

	public GUIWidget(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height, NVGColor borderColor,
			int border_width) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.hasBorder = true;
		this.borderColor = borderColor;
		this.borderWidth = border_width;

	}

	public GUIWidget(float real_x, float real_y, float real_width, float real_height) {
		this.real_x = real_x;
		this.real_y = real_y;
		this.real_width = real_width;
		this.real_height = real_height;
	}

	protected void drawBorder(long nvg) {
		if (!hasBorder)
			return;

		nvgBeginPath(nvg);

		// 上边框
		nvgRoundedRect(nvg, real_x - borderWidth, real_y - borderWidth, borderWidth * 2 + real_width, borderWidth, 0);
		// 左边框
		nvgRoundedRect(nvg, real_x - borderWidth, real_y, borderWidth, real_height, 0);
		// 下边框
		nvgRoundedRect(nvg, real_x - borderWidth, real_y + real_height, borderWidth * 2 + real_width, borderWidth, 0);
		// 右边框
		nvgRoundedRect(nvg, real_x + real_width, real_y, borderWidth, real_height, 0);

		nvgFillColor(nvg, borderColor);
		nvgFill(nvg);

	}

	protected void anim_tick() {
		mouseClicked = false;

		if (x != null)
			real_x = x.value;
		if (y != null)
			real_y = y.value;
		if (width != null)
			real_width = width.value;
		if (height != null)
			real_height = height.value;

		if (this.currentAnimation != null) {
			if (this.currentAnimation.tick(this)) {
				real_x = x.value;
				real_y = y.value;
				real_width = width.value;
				real_height = height.value;
				this.currentAnimation = null;
			}
		}

	}

	public abstract void draw(long nvg);

	public void size() {
		if (x != null) {
			x.eval();
			real_x = x.value;
		}
		if (y != null) {
			y.eval();
			real_y = y.value;
		}
		if (width != null) {
			width.eval();
			real_width = width.value;
		}
		if (height != null) {
			height.eval();
			real_height = height.value;
		}

	}

	public void setAnimation(String key) {
		this.currentAnimation = this.animations.get(key);
		if (this.currentAnimation != null) {
			this.currentAnimation.start();
		} else {
			Logger.warn("[GUI] Didn't found animation: " + key + "!");
		}
	}

	public static interface OnClickListener {
		public void onClick(int button);
	}

	public OnClickListener onClickListener = null;

	@Override
	public void close() throws Exception {

	}

}
