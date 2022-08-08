package xueli.game2;

public class Timer {

	private static final int DUE_TICK_PER_SECONDS = 20;
	private static final double DUE_SECOND_PER_TICK = 1.0 * 1000 / DUE_TICK_PER_SECONDS;

	private long lastTime = 0;

	private int numShouldTick = 0;
	private int numHasTick = 0;

	private long remain = 0;
	private float remainProgress = 0;

	public void tick() {
		if(lastTime == 0) {
			lastTime = System.currentTimeMillis();
			return;
		}

		long thisTime = System.currentTimeMillis();
		long delta = thisTime - lastTime;
		remain += delta;

		this.numShouldTick = (int) (this.remain / DUE_SECOND_PER_TICK);
		this.remain -= this.numShouldTick * DUE_SECOND_PER_TICK;
		this.numHasTick += this.numShouldTick;

		lastTime = thisTime;

	}

	public void runTick(Runnable run) {
		for (int i = 0; i < numShouldTick; i++) {
			run.run();
		}
	}

	public int getNumShouldTick() {
		return numShouldTick;
	}

	public float getRemainProgress() {
		return remainProgress;
	}

	public int getNumHasTick() {
		return numHasTick;
	}

}
