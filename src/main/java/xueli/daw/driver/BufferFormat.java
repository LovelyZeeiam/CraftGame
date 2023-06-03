package xueli.daw.driver;

import org.lwjgl.openal.AL11;

public enum BufferFormat {

	// For now it is the same as OpenAL because LovelyZeeiam doesn't know much about other format!
	MONO8, MONO16, STEREO8, STEREO16;
	
	// Will be moved when make ALDriver to interface!
	public static int getALFormat(BufferFormat format) {
		return switch (format){
		case MONO8 -> AL11.AL_FORMAT_MONO8;
		case MONO16 -> AL11.AL_FORMAT_MONO16;
		case STEREO8 -> AL11.AL_FORMAT_STEREO8;
		case STEREO16 -> AL11.AL_FORMAT_STEREO16;
		default -> throw new IllegalArgumentException("Unexpected value: " + format);
		};
	}
	
	public static BufferFormat getFromAL(int format) {
		return switch (format) {
		case AL11.AL_FORMAT_MONO8 -> MONO8;
		case AL11.AL_FORMAT_MONO16 -> MONO16;
		case AL11.AL_FORMAT_STEREO8 -> STEREO8;
		case AL11.AL_FORMAT_STEREO16 -> STEREO16;
		default -> throw new IllegalArgumentException("Unexpected value: " + format);
		};
	}

}
