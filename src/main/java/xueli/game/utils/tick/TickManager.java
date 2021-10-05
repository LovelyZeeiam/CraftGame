package xueli.game.utils.tick;

import java.util.ArrayList;

public class TickManager {
	
	private final int duration;
	
	private ArrayList<Tickable> tickables = new ArrayList<>();
	
	private long lastTick = 0;
	
	public TickManager(int duration) {
		this.duration = duration;
	}
	
	public void add(Tickable t) {
		tickables.add(t);
	}
	
	public boolean delete(Tickable t) {
		return tickables.remove(t);
	}
	
	public void tick() {
		long time = System.currentTimeMillis();
		if(time - lastTick > duration) {
			tickables.forEach(t -> t.tick(duration));
			this.lastTick = time;
		}
		
		long thisTime = System.currentTimeMillis();
		if(thisTime - time > duration) {
			System.err.println("Can't keep up! " + (thisTime - time) + "ms");
			
		}
		
	}

}
