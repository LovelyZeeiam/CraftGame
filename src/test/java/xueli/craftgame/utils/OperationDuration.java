package xueli.craftgame.utils;

public class OperationDuration {
	
	private long duration;
	private long lastOpr = 0;
	
	public OperationDuration(long duration) {
		this.duration = duration;
		
	}
	
	public boolean check() {
		long thisTime = System.currentTimeMillis();
		if(thisTime - lastOpr > duration) {
			lastOpr = thisTime;
			return true;
		}
		return false;
	}
	
	public void relieve() {
		lastOpr = 0;
	}
	
	public long getDuration() {
		return duration;
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
}
