package xueli.daw.driver;

import java.nio.IntBuffer;

import org.lwjgl.openal.AL11;
import org.lwjgl.system.MemoryUtil;

public class ALSpeaker {
	
	final int id;
	
	public ALSpeaker(int id) {
		this.id = id;
	}
	
	public void setBuffer(ALBuffer buffer) {
		if(buffer == null)
			AL11.alSourcei(this.id, AL11.AL_BUFFER, 0);
		else
			AL11.alSourcei(this.id, AL11.AL_BUFFER, buffer.id);
	}
	
	public void setPitch(float pitch) {
		AL11.alSourcef(this.id, AL11.AL_PITCH, pitch);
	}
	
	public void setGain(float gain) {
		AL11.alSourcef(this.id, AL11.AL_GAIN, gain);
	}
	
	public void setLooping(boolean loop) {
		AL11.alSourcei(this.id, AL11.AL_LOOPING, loop ? 1 : 0);
	}
	
	public void play() {
		AL11.alSourcePlay(this.id);
	}
	
	public void pause() {
		AL11.alSourcePause(this.id);
	}
	
	public void stop() {
		AL11.alSourceStop(this.id);
	}
	
	public void rewind() {
		AL11.alSourceRewind(this.id);
	}
	
	public void queueBuffer(ALBuffer... queue) {
		if(queue.length == 1) {
			AL11.alSourceQueueBuffers(this.id, queue[0].id);
			return;
		}
		
		// this time we can have full control of this memory
		IntBuffer arrQueueNames = MemoryUtil.memAllocInt(queue.length);
		
		for(ALBuffer buf : queue) { // Is this slower than directly access to array?
			arrQueueNames.put(buf.id);
		}
		arrQueueNames.flip();
		AL11.alSourceQueueBuffers(this.id, arrQueueNames);
		
		MemoryUtil.memFree(arrQueueNames);
		
	}
	
	public void unqueueBuffer(ALBuffer... queue) {
		// this time we must have full control of this memory
		IntBuffer arrQueueNames = MemoryUtil.memAllocInt(queue.length);
		
		for(ALBuffer buf : queue) { // Is this slower than directly access to array?
			arrQueueNames.put(buf.id);
		}
		arrQueueNames.flip();
		AL11.alSourceUnqueueBuffers(this.id, arrQueueNames);
		
		MemoryUtil.memFree(arrQueueNames);
		
	}

}
