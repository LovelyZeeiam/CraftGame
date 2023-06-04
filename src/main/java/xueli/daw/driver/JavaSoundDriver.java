package xueli.daw.driver;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class JavaSoundDriver {
	
//	private static final Logger LOGGER = new Logger();
	
	private final SourceDataLine sourceDataLine;
	private JavaSoundStream stream;
	
	public JavaSoundDriver(AudioFormat format) throws LineUnavailableException {
		this.sourceDataLine = AudioSystem.getSourceDataLine(format);
		this.sourceDataLine.open();
		
	}
	
	public JavaSoundStream start() {
		if(this.stream != null) return stream;
		
		this.sourceDataLine.start();
		return new JavaSoundStream() {
			
			@Override
			public void write(byte[] b, int offset, int length) {
				sourceDataLine.write(b, offset, length);
			}
			
			@Override
			public void close() {
				sourceDataLine.drain();
				sourceDataLine.stop();
				
				stream = null;
				
			}
		};
	}
	
	public void release() {
		this.sourceDataLine.close();
		
	}
	
}
