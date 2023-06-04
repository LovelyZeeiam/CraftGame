package xueli.daw.synthesizer;

import xueli.daw.DawContext;
import xueli.daw.Generator;

public class SineWaveSynthesizer implements Generator {

	public SineWaveSynthesizer() {
	}

	@Override
	public void progress(int[] dest, double time, DawContext context) {
		for(int i = 0; i < dest.length; i++) {
			dest[i] = (int) (Integer.MAX_VALUE * SynthesizerUtils.sine(440.0, time + i * DawContext.BUFFER_AHEAD_TIME / dest.length));
		}
		
	}

}
