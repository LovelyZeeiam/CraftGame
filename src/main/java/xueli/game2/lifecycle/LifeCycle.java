package xueli.game2.lifecycle;

public interface LifeCycle {
	
	public void init();

	/**
	 * In something about rendering, this is called "Render"
	 */
	public void tick();

	public void release();
	
}
