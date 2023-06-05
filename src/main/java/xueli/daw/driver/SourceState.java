package xueli.daw.driver;

import org.lwjgl.openal.AL11;

public enum SourceState {
	
	// For now it is the same as OpenAL because LovelyZeeiam doesn't know much about other format!
	INITIAL, PLAYING, PAUSED, STOPPED;
	
	// Will be moved when make ALDriver to interface!
	// Hard coded! wouldn't consider use a data structure to store this? —— LovelyZeeiam
	public static int getALState(SourceState state) {
		return switch (state){
		case INITIAL -> AL11.AL_INITIAL;
		case PLAYING -> AL11.AL_PLAYING;
		case PAUSED -> AL11.AL_PAUSED;
		case STOPPED -> AL11.AL_STOPPED;
		default -> throw new IllegalArgumentException("Unexpected value: " + state);
		};
	}
	
	public static SourceState getFromAL(int state) {
		return switch (state) {
		case AL11.AL_INITIAL -> INITIAL;
		case AL11.AL_PLAYING -> PLAYING;
		case AL11.AL_PAUSED -> PAUSED;
		case AL11.AL_STOPPED -> STOPPED;
		default -> throw new IllegalArgumentException("Unexpected value: " + state);
		};
	}
	
}
