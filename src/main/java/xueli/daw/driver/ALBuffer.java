package xueli.daw.driver;

import java.util.Objects;

import org.lwjgl.openal.AL11;

import xueli.utils.buffer.BufferSyncor;
import xueli.utils.buffer.LotsOfByteBuffer;

public class ALBuffer {
	
	final int id;
	
	BufferFormat format;
	int frequency;
	
	final BufferSyncor bufferManager = new BufferSyncor();
	
	public ALBuffer(int id) {
		this.id = id;
	}
	
	public void setBuffer(LotsOfByteBuffer buffer, BufferFormat format, int frequency) {
		this.format = format;
		this.frequency = frequency;
		this.bufferManager.updateBuffer(buffer);
		
	}
	
	public BufferSyncor.BackBuffer createBackBuffer(BufferFormat format, int frequency) {
		this.format = format;
		this.frequency = frequency;
		return this.bufferManager.createBackBuffer();
	}
	
	// Maybe here we have to do this ourselves
	public void doingSyncIfNecessary() {
		this.bufferManager.doingSyncIfNecessary(b -> AL11.alBufferData(this.id, BufferFormat.getALFormat(this.format), b.getBuffer(), this.frequency));
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ALBuffer other = (ALBuffer) obj;
		return id == other.id;
	}
	
//	public BufferState getState() {
//		return BufferState.getFromAL(AL11.algetbuffer);
//	}
	
}