package xueLi.gamengine.gui;

public class AnimationWait extends IAnimation {

	private int duration;

	public AnimationWait(int duration) {
		this.duration = duration;

	}

	@Override
	public boolean tick(GUIWidget widget) {
		if (System.currentTimeMillis() - startTime > duration)
			return true;
		return false;

	}

}
