package xueli.gamengine.view;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import xueli.gamengine.utils.EvalableFloat;

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

        nvgImagePattern(nvg, x.getValue(), y.getValue(), width.getValue(), height.getValue(), 0, textureID, 1, paint);
        nvgBeginPath(nvg);
        nvgRoundedRect(nvg, x.getValue(), y.getValue(), width.getValue(), height.getValue(), 0);
        nvgFillPaint(nvg, paint);
        nvgFill(nvg);

    }

    @Override
    public void close() throws Exception {
        super.close();
    }

}
