package xueli.daw.synthesizer;

public class SynthesizerUtils {
	
	public static double sine(double freq, double time) {
		return Math.sin(2.0*Math.PI*freq*time);
	}

	public static double sawtooth(double freq, double time) {
		return 2.0*(freq*time - Math.floor(freq*time + 0.5));
	}

	public static double triangle(double freq, double time) {
		return 2.0*Math.abs(sawtooth(freq, time))-1.0;
	}

}
