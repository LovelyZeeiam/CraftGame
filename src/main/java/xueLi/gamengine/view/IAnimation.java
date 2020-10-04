package xueLi.gamengine.view;

import java.util.HashMap;

import xueLi.gamengine.utils.Time;

public abstract class IAnimation {

	protected long startTime;
	
	public ViewWidget widget;

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

	/**
	 * @return if animation ends, it will return true. Otherwise return false.
	 */
	public boolean tick(HashMap<String, ViewWidget> widgets) { return true; }
	
	/**
	 * @return if animation ends, it will return true. Otherwise return false.
	 */
	public boolean tick(ViewWidget widget) { return true; }
	

}
