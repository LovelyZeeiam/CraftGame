package xueli.swingx;

import javax.swing.Timer;

public abstract class Service<T> {
	
	protected final T bean;
	private final Timer updateTimer;
	private boolean started = false;
	
	public Service(T bean, int intervalMs) {
		this.bean = bean;
		this.updateTimer = new Timer(intervalMs, e -> this.updateTimer());
		
	}
	
	public void start() {
		if(started) {
			System.err.println("Start twice? " + getClass().getName());
			return;
		}
		
		updateTimer.start();
		
	}
	
	protected abstract void updateTimer();
	
	public void stop() {
		if(!started) {
			System.out.println("Stop twice? " + getClass().getName());
			return;
		}
		
		updateTimer.stop();
		
	}
	
}
