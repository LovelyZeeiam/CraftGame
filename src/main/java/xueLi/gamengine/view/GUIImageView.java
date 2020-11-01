package xueLi.gamengine.view;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import xueLi.gamengine.utils.EvalableFloat;

import static org.lwjgl.nanovg.NanoVG.*;

public class GUIImageView extends ViewWidget {

    protected static NVGPaint paint;

    static {
        paint = NVGPaint.create();

    }

    protected int textureID;

    public GUIImageView(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height, int textureID) {
        super(x, y, width, height);
        this.textureID = textureID;
    }

    public GUIImageView(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height, int textureID,
                        NVGColor borderColor, int borderWidth) {
        super(x, y, width, height, borderColor, borderWidth);
        this.textureID = textureID;

    }

    @Override
    public void draw(long nvg) {
        paint.clear();

        super.anim_tick();
        super.drawBorder(nvg);

        nvgImagePattern(nvg, real_x, real_y, real_width, real_height, 0, textureID, 1, paint);
        nvgBeginPath(nvg);
        nvgRoundedRect(nvg, real_x, real_y, real_width, real_height, 0);
        nvgFillPaint(nvg, paint);
        nvgFill(nvg);

    }

    @Override
    public void close() throws Exception {
        super.close();
    }

}
