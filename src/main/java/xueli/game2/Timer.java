package xueli.game2;

public class Timer {

	private static final int DUE_TICK_PER_SECONDS = 20;
	private static final double DUE_SECOND_PER_TICK = 1.0 * 1000 / DUE_TICK_PER_SECONDS;
	private static final int MAX_TICK_PER_INVOKE = 100;

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
		this.remainProgress = (float) (this.remain / DUE_SECOND_PER_TICK);

		int numExceed = this.numShouldTick - MAX_TICK_PER_INVOKE;
		if(numExceed > 0) {
			System.out.printf("Can't keep up! Drop %d ticks%n", numExceed);
			this.numShouldTick = MAX_TICK_PER_INVOKE;
		}

		lastTime = thisTime;

	}

	@Deprecated
	public void runTick(Runnable tick) {
		for (int i = 0; i < numShouldTick; i++) {
			this.numHasTick++;
			tick.run();
		}
	}

	public float getRemainProgress() {
		return remainProgress;
	}

	public int getNumHasTick() {
		return numHasTick;
	}
	
	public int getNumShouldTick() {
		return numShouldTick;
	}

}
