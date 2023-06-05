package xueli.daw.driver;

import org.lwjgl.openal.AL11;

public enum SourceType {
	
	// For now it is the same as OpenAL because LovelyZeeiam doesn't know much about other format!
	UNDETERMINED, STATIC, STREAMING;
	
	// Will be moved when make ALDriver to interface!
	// Hard coded! wouldn't consider use a data structure to store this? —— LovelyZeeiam
	public static SourceType getFromAL(int state) {
		return switch (state) {
		case AL11.AL_UNDETERMINED -> UNDETERMINED;
		case AL11.AL_STATIC -> STATIC;
		case AL11.AL_STREAMING -> STREAMING;
		default -> throw new IllegalArgumentException("Unexpected value: " + state);
		};
	}
	
}
