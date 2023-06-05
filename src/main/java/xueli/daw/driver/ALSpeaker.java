package xueli.daw.driver;

import java.nio.IntBuffer;
import org.lwjgl.openal.AL11;
import org.lwjgl.system.MemoryUtil;

public class ALSpeaker {
	
	final int id;
	final ALDriver context;
	
	public ALSpeaker(int id, ALDriver context) {
		this.id = id;
		this.context = context;
	}
	
	public void setBuffer(ALBuffer buffer) {
		if(buffer == null)
			AL11.alSourcei(this.id, AL11.AL_BUFFER, 0);
		else {
			AL11.alSourcei(this.id, AL11.AL_BUFFER, buffer.id);
		}
		
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
	
	public SourceState getState() {
		int state = AL11.alGetSourcei(this.id, AL11.AL_SOURCE_STATE);
		return SourceState.getFromAL(state);
	}
	
	public SourceType getType() {
		int type = AL11.alGetSourcei(this.id, AL11.AL_SOURCE_TYPE);
		return SourceType.getFromAL(type);
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
	
	// Unqueue the buffer and put it in the queue
	public void unqueueBuffer(ALBuffer[] dest) {
		int[] temp = new int[dest.length];
		AL11.alSourceUnqueueBuffers(this.id, temp);
		for(int i = 0; i < dest.length; i++) {
			dest[i] = context.getBufferFromId(temp[i]);
		}
	}
	
	public int queryProcessedBufferCount() {
		return AL11.alGetSourcei(this.id, AL11.AL_BUFFERS_PROCESSED);
	}
	
	public void queryProcessedBuffer(ALBuffer[] dest) {
		int[] temp = new int[dest.length];
		AL11.alGetSourceiv(this.id, AL11.AL_BUFFERS_PROCESSED, temp);
		for(int i = 0; i < dest.length; i++) {
			dest[i] = context.getBufferFromId(temp[i]);
		}
		
	}

}
