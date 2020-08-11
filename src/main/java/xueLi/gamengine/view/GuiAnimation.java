package xueLi.gamengine.view;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import xueLi.gamengine.utils.EvalableFloat;

public class GuiAnimation extends IAnimation {

	private HashMap<String, EvalableFloat[]> parameters = new HashMap<String, EvalableFloat[]>();
	private float duration;
	private boolean stay;

	public GuiAnimation(JsonObject parameterJsonObject, int duration, boolean stay) {
		this.duration = duration;
		this.stay = stay;
		for (Map.Entry<String, JsonElement> e : parameterJsonObject.entrySet()) {
			JsonObject value = e.getValue().getAsJsonObject();
			parameters.put(e.getKey(), new EvalableFloat[] { new EvalableFloat(value.get("from").getAsString()),
					new EvalableFloat(value.get("to").getAsString()), });
		}
	}

	// Smooth the noise
	private float interpolate(float a, float b, float blend) {
		double theta = blend * Math.PI;
		float f = (float) ((1f - Math.cos(theta)) * 0.5f);
		return a * (1f - f) + b * f;
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
