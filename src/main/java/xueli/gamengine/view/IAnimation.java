package xueli.gamengine.view;

import xueli.gamengine.utils.Time;
import xueli.gamengine.view.anim2d.Constant;

import java.util.HashMap;

public abstract class IAnimation {

	public ViewWidget widget;
	protected long startTime;

	public void start(HashMap<String, ViewWidget> widgets, ViewWidget widget) {
		startTime = Time.thisTime;
		this.widget = widget;

	}

	// Smooth the noise
	protected float interpolate(float a, float b, float blend) {
		double theta = blend * Math.PI;
		float f = (float) ((1f - Math.cos(theta)) * 0.5f);
		return a * (1f - f) + b * f;
	}

	public boolean tick(HashMap<String, ViewWidget> widgets) {
		return Constant.COMPONENT_CAN_BE_DISPOSED;
	}

	public boolean tick(ViewWidget widget) {
		return Constant.COMPONENT_CAN_BE_DISPOSED;
	}

}
