package xueli.daw.driver;

import org.lwjgl.openal.AL11;

public enum BufferState {
	
	// For now it is the same as OpenAL because LovelyZeeiam doesn't know much about other format!
	UNUSED, PENDING, PROCESSED;
	
	// Will be moved when make ALDriver to interface!
	public static int getALFormat(BufferState format) {
		return switch (format){
		case UNUSED -> AL11.AL_UNUSED;
		case PENDING -> AL11.AL_PENDING;
		case PROCESSED -> AL11.AL_PROCESSED;
		default -> throw new IllegalArgumentException("Unexpected value: " + format);
		};
	}
	
	public static BufferState getFromAL(int format) {
		return switch (format) {
		case AL11.AL_UNUSED -> UNUSED;
		case AL11.AL_PENDING -> PENDING;
		case AL11.AL_PROCESSED -> PROCESSED;
		default -> throw new IllegalArgumentException("Unexpected value: " + format);
		};
	}

}
