package xueli.daw.driver;

import xueli.daw.synthesizer.SynthesizerUtils;
import xueli.utils.buffer.LotsOfByteBuffer;

//@SuppressWarnings("unused")
public class ALDriverTest {

	// Why does JUnit Test annotation not working here? "TestEngine with ID
	// 'junit-jupiter' failed to discover tests"
	public static void main(String[] args) {
		ALDriver driver = new ALDriver(null);

		// Create a simple sine wave
		final int sampleRate = 44100;
		LotsOfByteBuffer sinWaveAudioBuffer = new LotsOfByteBuffer(sampleRate);
		for (int i = 0; i < sampleRate; i++) {
			double src = SynthesizerUtils.triangle(440, (double) i / sampleRate);
			src = Math.max(-1.0, src);
			src = Math.min(1.0, src);
			short sample = (short) (src * 32767.0);
//			System.out.println(sample);

			sinWaveAudioBuffer.putShort(sample);
		}
		sinWaveAudioBuffer.setReadWrite(true);

		// Create Buffer
		var buffer = driver.createBuffer();
		buffer.setBuffer(sinWaveAudioBuffer, BufferFormat.MONO16, sampleRate); // MONO8 can create big distortion?
		buffer.doingSyncIfNecessary();

		// Create Speaker
		var speaker = driver.createSpeaker();
		speaker.setBuffer(buffer);
		speaker.play();

		synchronized (Thread.currentThread()) {
			try {
				Thread.currentThread().wait(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		sinWaveAudioBuffer.release();
		driver.release();

	}

}
