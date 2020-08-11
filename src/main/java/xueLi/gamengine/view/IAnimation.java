package xueLi.gamengine.view;

public abstract class IAnimation {

	protected long startTime;

	public void start() {
		startTime = System.currentTimeMillis();

	}

	/**
	 * @return if animation ends, it will return true. Otherwise return false.
	 */
	public abstract boolean tick(ViewWidget widget);

}