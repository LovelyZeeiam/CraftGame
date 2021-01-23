package xueli.gamengine.view;

import xueli.gamengine.utils.EvalableFloat;
import xueli.gamengine.utils.Time;
import xueli.gamengine.view.anim2d.Constant;

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
		long delta_time = Time.thisTime - startTime;
		if (delta_time > duration) {
			if (!stay) {
				for (Map.Entry<String, EvalableFloat[]> e : parameters.entrySet()) {
					switch (e.getKey()) {
					case "width":
						widget.width.setExpression(e.getValue()[0].getExpression());
						break;
					case "height":
						widget.height.setExpression(e.getValue()[0].getExpression());
						break;
					case "x":
						widget.x.setExpression(e.getValue()[0].getExpression());
						break;
					case "y":
						widget.y.setExpression(e.getValue()[0].getExpression());
						break;
					default:
						break;
					}
				}
			}
			return Constant.COMPONENT_CAN_BE_DISPOSED;
		}
		for (Map.Entry<String, EvalableFloat[]> e : parameters.entrySet()) {
			EvalableFloat[] value = e.getValue();

			String expression = String.format("(%s) + ((%s) - (%s)) * (1.0 - Math.cos((%f) * Math.PI)) / 2.0",
					value[0].getExpression(), value[1].getExpression(), value[0].getExpression(),
					(float) delta_time / duration);

			switch (e.getKey()) {
			case "width":
				widget.width.setExpression(expression);
				break;
			case "height":
				widget.height.setExpression(expression);
				break;
			case "x":
				widget.x.setExpression(expression);
				break;
			case "y":
				widget.y.setExpression(expression);
				break;
			default:
				break;
			}

		}
		return Constant.COMPONENT_CANT_BE_DISPOSED_YET;
	}

}
