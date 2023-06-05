package xueli.daw.driver;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC11;
import org.lwjgl.openal.ALCCapabilities;
import xueli.utils.logger.Logger;

// The driver is designed for DAW so it doesn't provide functions like "effects" or "positions".
// But this group of classes can provide.
// Not used!
public class ALDriver {
	
	private static final Logger LOGGER = new Logger();
	
	private final long context;
	private HashMap<Integer, ALBuffer> bufferPool = new HashMap<>();
	
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
        
		this.printDeviceInfo(device);
        
	}
	
	private void printDeviceInfo(long device) {
		// Keep the same code structure as OpenGL :}
		String nameString = AL11.alGetString(AL11.AL_VENDOR);
		String platform = AL11.alGetString(AL11.AL_RENDERER);
		String version = AL11.alGetString(AL11.AL_VERSION);
		LOGGER.warning("[DeviceInfo] OpenAL: " + nameString + ", " + platform + ", " + version);

		
	}
	
	public ALSpeaker createSpeaker() {
		int speaker = AL11.alGenSources();
		return new ALSpeaker(speaker, this);
	}
	
	public void releaseSpeaker(ALSpeaker speaker) {
		AL11.alDeleteSources(speaker.id);
		
	}
	
	public ALBuffer createBuffer() {
		int name = AL11.alGenBuffers();
		var bufferObj = new ALBuffer(name);
		this.bufferPool.put(name, bufferObj);
		return bufferObj;
	}
	
	public ALBuffer getBufferFromId(int id) {
		return bufferPool.get(id);
	}
	
	public void releaseBuffer(ALBuffer buffer) {
		AL11.alDeleteBuffers(buffer.id);
		this.bufferPool.remove(buffer.id);
		
	}
	
	public void release() {
		ALC11.alcMakeContextCurrent(0);
		ALC11.alcDestroyContext(this.context);
		
	}

}
