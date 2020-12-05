package xueli.gamengine.view;

import org.lwjgl.nanovg.NVGColor;
import xueli.gamengine.utils.Display;
import xueli.gamengine.utils.EvalableFloat;
import xueli.gamengine.view.text.KeyDesc;
import xueli.gamengine.view.text.KeyType;

import static org.lwjgl.nanovg.NanoVG.*;

public abstract class ViewWidget implements AutoCloseable {

    public float real_x, real_y, real_width, real_height;
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

    public ViewWidget(float real_x, float real_y, float real_width, float real_height) {
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
        return cursorX > real_x & cursorX < real_x + real_width & cursorY > real_y & cursorY < real_y + real_height;
    }

    @KeyDesc
    public void keyDown(int key, KeyType type) {

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
