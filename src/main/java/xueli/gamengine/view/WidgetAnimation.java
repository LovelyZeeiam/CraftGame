package xueli.gamengine.view;

import xueli.gamengine.utils.EvalableFloat;

import java.util.HashMap;
import java.util.Map;

public class WidgetAnimation extends IAnimation {

    private HashMap<String, EvalableFloat[]> parameters;
    private float duration;
    private boolean stay;

    public WidgetAnimation(HashMap<String, EvalableFloat[]> paramsHashMap, float duration, boolean stay) {
        this.parameters = paramsHashMap;
        this.duration = duration;
        this.stay = stay;

    }

    @Override
    public boolean tick(ViewWidget widget) {
        long delta_time = System.currentTimeMillis() - startTime;
        if (delta_time > duration) {
            if (stay) {
                for (Map.Entry<String, EvalableFloat[]> e : parameters.entrySet()) {
                    switch (e.getKey()) {
                        case "width":
                            widget.width.expression = e.getValue()[1].expression;
                            break;
                        case "height":
                            widget.height.expression = e.getValue()[1].expression;
                            break;
                        case "x":
                            widget.x.expression = e.getValue()[1].expression;
                            break;
                        case "y":
                            widget.y.expression = e.getValue()[1].expression;
                            break;
                        default:
                            break;
                    }
                }
            }
            widget.size();
            return true;
        }
        for (Map.Entry<String, EvalableFloat[]> e : parameters.entrySet()) {
            EvalableFloat[] value = e.getValue();
            // from
            value[0].eval();
            // to
            value[1].eval();

            float current_value = interpolate(value[0].value, value[1].value, (float) delta_time / duration);
            switch (e.getKey()) {
                case "width":
                    widget.real_width = current_value;
                    break;
                case "height":
                    widget.real_height = current_value;
                    break;
                case "x":
                    widget.real_x = current_value;
                    break;
                case "y":
                    widget.real_y = current_value;
                    break;
                default:
                    break;
            }

        }
        return false;
    }

}
