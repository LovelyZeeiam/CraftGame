package xueLi.gamengine.view;

import org.lwjgl.nanovg.NVGColor;
import xueLi.gamengine.utils.EvalableFloat;

public class GUITextBox extends ViewWidget {


    public GUITextBox(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height, NVGColor borderColor, int border_width) {
        super(x, y, width, height, borderColor, border_width);

    }

    @Override
    public void draw(long nvg) {

    }

}
