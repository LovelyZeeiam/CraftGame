package xueli.daw;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

import xueli.daw.driver.JavaSoundDriver;
import xueli.daw.driver.JavaSoundStream;

public class DawContext {
	
	public static final AudioFormat GLOBAL_FORMAT = new AudioFormat(44100, 16, 1, true, false);
	public static final int BUFFER_SIZE = 2048;
	public static final double BUFFER_AHEAD_TIME = (double) BUFFER_SIZE / GLOBAL_FORMAT.getSampleRate();
	
	public static final int SAMPLE_BYTES_COUNT = GLOBAL_FORMAT.getSampleSizeInBits() >> 3;
	public static final int MAX_SAMPLE_VALUE = (1 << (GLOBAL_FORMAT.getSampleSizeInBits() - 1)) - 1;
	
	private final JavaSoundDriver driver;
	final JavaSoundStream streamer;
	
	private final ChannelManager channels;
	
	public DawContext() {
		try {
			this.driver = new JavaSoundDriver(GLOBAL_FORMAT);
		} catch (LineUnavailableException e) {
			throw new RuntimeException(e);
		}
		this.streamer = driver.start();
		
		this.channels = new ChannelManager(this);
		
	}
	
	public ChannelManager getChannelManager() {
		return channels;
	}
	
	public void tick() {
		this.channels.tick();
		
	}
	
	public void release() {
		this.streamer.close();
		this.driver.release();
		
	}
	
}
