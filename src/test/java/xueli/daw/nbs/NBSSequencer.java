package xueli.daw.nbs;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

// TODO: Add start time!
// Maybe this object can be constructed from a NBS object!
// May be an Sequencer interface!
public class NBSSequencer {
	
	private final double tickPerSecond;
	private final TreeMap<Long, ArrayList<NoteBlock>> timeline = new TreeMap<>();
	
	public NBSSequencer(double tickPerSecond, List<NoteBlock> src) {
		this.tickPerSecond = tickPerSecond;
		this.buildTimeline(src);
		
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
	public boolean progress(long currentTimeMills, List<NoteBlock> dest) {
		Long lowerKey = currentTimeMills;
		boolean exists = false;
		while((lowerKey = this.timeline.lowerKey(lowerKey)) != null) {
			if(lowerKey.longValue() < lastTime) break;
			exists = true;
			dest.addAll(timeline.get(lowerKey));
		}
		
		lastTime = currentTimeMills;
		return exists;
	}
	
	public long getCurrentTime() {
		return this.lastTime;
	}

}
