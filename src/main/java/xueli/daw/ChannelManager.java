package xueli.daw;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

// The manager just store the channel, so the life-cycle should be managed by the user!
public class ChannelManager {
	
	private final DawContext context;
	private final ArrayList<Channel> channels = new ArrayList<>();
	
	private double time = 0.0;
	
	public ChannelManager(DawContext context) {
		this.context = context;
		
	}
	
	private int[] buffer;
	private byte[] destBuffer = new byte[DawContext.BUFFER_SIZE * DawContext.GLOBAL_FORMAT.getSampleSizeInBits()];
	
	public void tick() {
		time += DawContext.BUFFER_AHEAD_TIME;
		
		AtomicBoolean firstApply = new AtomicBoolean(true);
		
		channels.parallelStream()
			.map(c -> c.tick(time))
			// I want to make a collector myself but seems can't
			// And I don't want to collect them either
			.forEachOrdered(i -> {
				if(i == null) return;
				if(firstApply.get()) {
					buffer = i;
					firstApply.set(false);
				} else {
					for(int j = 0; j < DawContext.BUFFER_SIZE; j++) {
						// Blend
						buffer[j] = (buffer[j] + i[j]) / 2;
					}
				}
			});
		
		if(buffer == null) return;
		
		// Convert to sample bit
		for(int i = 0; i < DawContext.BUFFER_SIZE; i++) {
			double percentage = (double) buffer[i] / Integer.MAX_VALUE;
			percentage = Math.min(1.0, percentage);
			percentage = Math.max(-1.0, percentage);
			
			int sampleValue = (int) (percentage * DawContext.MAX_SAMPLE_VALUE);
			System.out.println(sampleValue);
			for(int j = 0; j < DawContext.SAMPLE_BYTES_COUNT; j++) {
				// little-endian
				destBuffer[i * DawContext.SAMPLE_BYTES_COUNT + j] = (byte) ((sampleValue >> (j * 8)) & (1 << 8 - 1));
			}
			
			// Set sign bit
			if(sampleValue < 0) {
				destBuffer[i * DawContext.SAMPLE_BYTES_COUNT] |= (1 << 8);
			}
			
		}
		
		context.streamer.write(destBuffer);
		
	}
	
	public int addChannel(Channel channel) {
		int index = this.channels.size();
		this.channels.add(channel);
		return index;
	}
	
	public boolean remove(Channel channel) {
		return this.channels.remove(channel);
	}
	
	public Channel removeChannel(int index) {
		return this.channels.remove(index);
	}
	
	public int getChannelsCount() {
		return this.channels.size();
	}
	
	public Channel getChannelFromIndex(int i) {
		return this.channels.get(i);
	}
	
}
