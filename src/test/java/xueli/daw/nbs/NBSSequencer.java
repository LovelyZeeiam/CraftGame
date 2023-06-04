package xueli.daw.nbs;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class NBSSequencer {
	
	private final long startTime;
	
	private final double tickPerSecond;
	private final TreeMap<Long, ArrayList<NoteBlock>> timeline = new TreeMap<>();
	
	public NBSSequencer(double tickPerSecond, List<NoteBlock> src) {
		this.tickPerSecond = tickPerSecond;
		this.buildTimeline(src);
		this.startTime = System.currentTimeMillis();
		
	}
	
	private void buildTimeline(List<NoteBlock> src) {
		src.forEach(n -> {
			this.addTimelineItem((long)(n.getTick() * 1000 / tickPerSecond), n);
		});
	}
	
	private void addTimelineItem(long time, NoteBlock item) {
		timeline.computeIfAbsent(time, t -> new ArrayList<>()).add(item);
	}
	
	private long lastTime = 0;
	
	// The return value indicated whether the music reaches the end.
	public boolean read(List<NoteBlock> dest) {
		long currentTime = System.currentTimeMillis() - this.startTime;
		
		Long lowerKey = currentTime;
		boolean exists = false;
		while((lowerKey = this.timeline.lowerKey(lowerKey)) != null) {
			if(lowerKey.longValue() < lastTime) break;
			exists = true;
			dest.addAll(timeline.get(lowerKey));
		}
		
		lastTime = currentTime;
		return exists;
	}

}
