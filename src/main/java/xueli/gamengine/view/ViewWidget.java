package xueli.gamengine.view;

import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRect;

import org.lwjgl.nanovg.NVGColor;

import xueli.gamengine.utils.Display;
import xueli.gamengine.utils.EvalableFloat;
import xueli.gamengine.view.anim2d.Constant;
import xueli.gamengine.view.text.KeyDesc;
import xueli.gamengine.view.text.KeyType;

public abstract class ViewWidget implements AutoCloseable {

	public OnClickListener onClickListener = null;
	protected boolean hasBorder = false;
	protected NVGColor borderColor;
	protected int borderWidth;
	EvalableFloat x, y, width, height;
	boolean isSelectedLastTime = false;
	private WidgetAnimation currentAnimation = null;

	public ViewWidget(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		anim_tick();

	}

	public ViewWidget(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height, NVGColor borderColor,
			int border_width) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.hasBorder = true;
		this.borderColor = borderColor;
		this.borderWidth = border_width;

	}

	protected void drawBorder(long nvg) {
		if (!hasBorder)
			return;

		nvgBeginPath(nvg);

		// 上边框
		nvgRoundedRect(nvg, x.getValue() - borderWidth, y.getValue() - borderWidth, borderWidth * 2 + width.getValue(),
				borderWidth, 0);
		// 左边框
		nvgRoundedRect(nvg, x.getValue() - borderWidth, y.getValue(), borderWidth, height.getValue(), 0);
		// 下边框
		nvgRoundedRect(nvg, x.getValue() - borderWidth, y.getValue() + height.getValue(),
				borderWidth * 2 + width.getValue(), borderWidth, 0);
		// 右边框
		nvgRoundedRect(nvg, x.getValue() + width.getValue(), y.getValue(), borderWidth, height.getValue(), 0);

		nvgFillColor(nvg, borderColor);
		nvgFill(nvg);

	}

	protected void anim_tick() {
		if (this.currentAnimation != null) {
			if (this.currentAnimation.tick(this) == Constant.COMPONENT_CAN_BE_DISPOSED) {
				this.currentAnimation = null;
			}
		}

	}

	public abstract void draw(long nvg);

	public void size() {
		if (x != null) {
			x.needEvalAgain();
		}
		if (y != null) {
			y.needEvalAgain();
		}
		if (width != null) {
			width.needEvalAgain();
		}
		if (height != null) {
			height.needEvalAgain();
		}

	}

	public void setAnimation(WidgetAnimation anim) {
		this.currentAnimation = anim;
		if (this.currentAnimation != null) {
			this.currentAnimation.start(null, this);
		}
	}

	protected boolean isMouseHover() {
		// 鼠标位置
		int cursorX = Display.currentDisplay.getMouseX();
		int cursorY = Display.currentDisplay.getMouseY();
		return cursorX > x.getValue() & cursorX < x.getValue() + width.getValue() & cursorY > y.getValue()
				& cursorY < y.getValue() + height.getValue();
	}

	@KeyDesc
	public void keyDown(int key, KeyType type) {

	}

	public void scroll(float scroll) {

	}

	@Override
	public void close() throws Exception {

	}

	public static interface OnClickListener {
		/**
		 * 点击监听事件
		 *
		 * @param button  鼠标按钮id
		 * @param offsetX 鼠标点击位置关于控件左上角的位移
		 * @param offsetY 鼠标点击位置关于控件右上角的位移
		 */
		public void onClick(int button, int action, double offsetX, double offsetY);
	}

}
