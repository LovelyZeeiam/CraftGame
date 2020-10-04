package xueLi.gamengine.view;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import xueLi.gamengine.utils.EvalableFloat;
import xueLi.gamengine.utils.Logger;
import xueLi.gamengine.utils.Time;

public class GuiAnimation extends IAnimation {

	private HashMap<String, HashMap<String, EvalableFloat[]>> parameters = new HashMap<String, HashMap<String,EvalableFloat[]>>();
	private float duration;
	private boolean stay;

	public GuiAnimation(JsonObject parameterJsonObject, int duration, boolean stay) {
		this.duration = duration;
		this.stay = stay;
		for (Map.Entry<String, JsonElement> e1 : parameterJsonObject.entrySet()) {
			String widgetString = e1.getKey();
			JsonObject params = e1.getValue().getAsJsonObject();
			HashMap<String, EvalableFloat[]> paramHashMap = new HashMap<String, EvalableFloat[]>();
			for(Map.Entry<String, JsonElement> e : params.entrySet()) {
				String paramType = e.getKey();
				JsonObject fromAndTo = e.getValue().getAsJsonObject();
				paramHashMap.put(paramType, new EvalableFloat[] {
					new EvalableFloat(fromAndTo.get("from").getAsString()),
					new EvalableFloat(fromAndTo.get("to").getAsString())
				});
			}
			this.parameters.put(widgetString, paramHashMap);
		}
	}
	
	@Override
	public void start(HashMap<String, ViewWidget> widgets, ViewWidget viewWidget) {
		for(Map.Entry<String, HashMap<String, EvalableFloat[]>> entry : parameters.entrySet()) {
			ViewWidget widget = widgets.get(entry.getKey());
			if(widget == null) {
				Logger.error("[Anim] Found no widget called: " + entry.getKey() + "!");
				continue;
			}
			
			widget.setAnimation(new WidgetAnimation(entry.getValue(), duration, stay));
			
		}
		super.start(widgets, viewWidget);
	}
	
	private boolean tick() {
		if(Time.thisTime - startTime > duration) return true;
		return false;
	}
	
	@Override
	public boolean tick(HashMap<String, ViewWidget> widgets) {
		return tick();
	}
	
	@Override
	public boolean tick(ViewWidget widget) {
		return tick();
	}

}
