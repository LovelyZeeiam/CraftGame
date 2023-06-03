package xueli.daw.driver;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC11;
import org.lwjgl.openal.ALCCapabilities;

public class ALDriver {
	
	private final long context;
	
	public ALDriver(ByteBuffer deviceSpecifier) {
		long device = ALC11.alcOpenDevice(deviceSpecifier);
		if(device == 0) {
			throw new RuntimeException("Can't open OpenAL device!");
		}
		
		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        this.context = ALC11.alcCreateContext(device, (IntBuffer) null);
        if (context == 0) {
            throw new IllegalStateException("Failed to create OpenAL context.");
        }
        ALC11.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
		
        
	}
	
	public ALSpeaker createSpeaker() {
		int speaker = AL11.alGenSources();
		return new ALSpeaker(speaker);
	}
	
	public void releaseSpeaker(ALSpeaker speaker) {
		AL11.alDeleteSources(speaker.id);
		
	}
	
	public ALBuffer createBuffer() {
		int buffer = AL11.alGenBuffers();
		return new ALBuffer(buffer);
	}
	
	public void releaseBuffer(ALBuffer buffer) {
		AL11.alDeleteBuffers(buffer.id);
		
	}
	
	public void release() {
		ALC11.alcMakeContextCurrent(0);
		ALC11.alcDestroyContext(this.context);
		
	}

}
