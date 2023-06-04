package xueli.daw.driver;

import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.SourceDataLine;

public class JavaSoundOutputStream extends OutputStream {
	
	private final SourceDataLine dataLine;
	
	public JavaSoundOutputStream(SourceDataLine dataLine) {
		this.dataLine = dataLine;
	}
	
	private final byte[] buffer = new byte[1];
	
	@Override
	public void write(int b) throws IOException {
		buffer[0] = (byte) b;
		dataLine.write(buffer, 0, 1);
	}
	
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		dataLine.write(b, off, len);
	}

}
