package xueli.daw.driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.openal.AL11;

import xueli.daw.nbs.NBSInputStream;
import xueli.daw.nbs.NBSSequencer;
import xueli.daw.nbs.NoteBlock;
import xueli.daw.synthesizer.SynthesizerUtils;
import xueli.utils.buffer.LotsOfByteBuffer;

public class ALStreamTest {
	
	private static interface BufferPoolListener {
		
		default public void onNewBufferAllocated(LotsOfByteBuffer buffer) {}
		default public void onBufferMarkUsing(LotsOfByteBuffer buffer) {}
		default public void onBufferRecycled(LotsOfByteBuffer buffer) {}
		default public void beforeBufferReleased(LotsOfByteBuffer buffer) {}
		
	}
	
	private static interface MemoryHandler {
		
		public LotsOfByteBuffer getBuffer();
		public void release();
		
	}
	
	// The memory is managed by the pool!
	// Not thread safe :{
	private static class BufferPool implements AutoCloseable {
		
		private final int perSize;
		
		private final ArrayList<LotsOfByteBuffer> allocated = new ArrayList<>();
		private final ArrayList<LotsOfByteBuffer> freed = new ArrayList<>();
		
		private final ArrayList<BufferPoolListener> listeners = new ArrayList<>();
		
		public BufferPool(int perSize) {
			this.perSize = perSize;
		}
		
		public void initialSpare(int initialCount) {
			for(int i = 0; i < initialCount; i++) {
				this.spareMore();
			}
		}
		
		private void spareMore() {
			var newBuffer = new LotsOfByteBuffer(this.perSize);
			freed.add(newBuffer);
			listeners.forEach(l -> l.onNewBufferAllocated(newBuffer));
			
		}
		
		public MemoryHandler spare() {
			if(freed.size() == 0) {
				this.spareMore();
			}
			
			final var buffer = freed.remove(freed.size() - 1);
			buffer.setReadWrite(false);
			allocated.add(buffer);
			
			listeners.forEach(l -> l.onBufferMarkUsing(buffer));
			
			return new MemoryHandler() {
				
				@Override
				public LotsOfByteBuffer getBuffer() {
					return buffer;
				}
				
				@Override
				public void release() {
					allocated.remove(buffer);
					freed.add(buffer);
					listeners.forEach(l -> l.onBufferRecycled(buffer));
				}
				
			};
		}
		
		public void addListener(BufferPoolListener listener) {
			this.listeners.add(listener);
		}
		
		public void removeListener(BufferPoolListener listener) {
			this.listeners.remove(listener);
		}
		
		private void freeBuffer(LotsOfByteBuffer buffer) {
			listeners.forEach(l -> l.beforeBufferReleased(buffer));
			buffer.release();
			
		}
		
		public int getAllocatedCount() {
			return this.allocated.size();
		}
		
		public int getFreeCount() {
			return this.allocated.size();
		}
		
		@Override
		public void close() throws Exception {
			this.allocated.forEach(this::freeBuffer);
			this.freed.forEach(this::freeBuffer);
			
		}
		
	}
	
	public static final int SAMPLE_RATE = 44100;
	public static final double PER_SAMPLE_LENGTH = 1.0 / SAMPLE_RATE;
	public static final int BUFFER_SAMPLE_COUNT = 2048;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// Read Note File
		var nbsIn = new NBSInputStream(new FileInputStream("res/music/ni_ting_de_dao.nbs"));
		List<NoteBlock> notes = nbsIn.readNoteBlocks();
		double tickPerSecond = nbsIn.getTempo() / 100.0;
		double musicLength = nbsIn.getSongLength() / tickPerSecond * 1000.0;
		nbsIn.close();
		
		// Create Driver
		var driver = new ALDriver(null);
		var streamSpeaker = driver.createSpeaker();
		
		// Create Memory Pool
		// Each memory buffer in the pool can be mapped to a buffer in OpenAL
		BufferPool pool = new BufferPool(BUFFER_SAMPLE_COUNT * 2);
		HashMap<LotsOfByteBuffer, ALBuffer> bufferMap = new HashMap<>();
		pool.addListener(new BufferPoolListener() {
			@Override
			public void onNewBufferAllocated(LotsOfByteBuffer memory) {
				var buffer = driver.createBuffer();
				bufferMap.put(memory, buffer);
			}
			
			@Override
			public void beforeBufferReleased(LotsOfByteBuffer memory) {
				var buffer = bufferMap.remove(memory);
				driver.releaseBuffer(buffer);
				
			}
		});
		pool.initialSpare(16);
		
		HashMap<ALBuffer, MemoryHandler> bufferHandlerMap = new HashMap<>();
		
		// Create Sequencer
		record PlayingNote(double startTime, NoteBlock block) {}
		ArrayList<PlayingNote> playingNotes = new ArrayList<>();
		
		ArrayList<NoteBlock> sequencerDest = new ArrayList<>();
		NBSSequencer sequencer = new NBSSequencer(tickPerSecond, notes);
		
		// Play Notes
		final int endBufferCount = (int) (musicLength + 1) * SAMPLE_RATE / BUFFER_SAMPLE_COUNT;
		int bufferedCount = 0;
		double bufferTime = 0.0;
		
		while(true) {
			long startTimestamp = System.currentTimeMillis();
			
			// free used buffer
			{
				int usedBufferCount = streamSpeaker.queryProcessedBufferCount();
				var usedBuffers = new ALBuffer[usedBufferCount];
				streamSpeaker.unqueueBuffer(usedBuffers);
				
				for(var buf : usedBuffers) {
					if(buf == null) continue;
					var handler = bufferHandlerMap.remove(buf);
					handler.release();
					System.out.println("[FREE] " + handler.toString());
				}
				
			}
			
			System.out.println(streamSpeaker.queryProcessedBufferCount());
			
			// store buffer
			while(pool.getAllocatedCount() < 20) {
				var handler = pool.spare();
				System.out.println("[ALLOC] " + handler.toString());
				var memory = handler.getBuffer();
				var alBuffer = bufferMap.get(memory);
				bufferHandlerMap.put(alBuffer, handler);
				
				for(int i = 0; i < BUFFER_SAMPLE_COUNT; i++) {
					bufferTime = (bufferedCount * BUFFER_SAMPLE_COUNT + i) * PER_SAMPLE_LENGTH;
					
					// do sequencer
					{
						sequencer.progress((int)(bufferTime * 1000), sequencerDest);
						for(var event : sequencerDest) {
							playingNotes.add(new PlayingNote(bufferTime, event));
						}
						sequencerDest.clear();
					}
					
					// write buffer
					{
						double thisValue = SynthesizerUtils.sine(440.0, bufferTime);
						memory.putShort((short) (thisValue * 32767.0));
					}
					
				}
				
				bufferedCount++;
				
				memory.setReadWrite(true);
				alBuffer.setBuffer(memory, BufferFormat.MONO16, SAMPLE_RATE);
				alBuffer.doingSyncIfNecessary();
				streamSpeaker.queueBuffer(alBuffer);
				
			}
			
			System.out.println(streamSpeaker.queryProcessedBufferCount());
			
			System.out.println("[BufferTime] " + bufferedCount + "," + bufferTime);
			
			int error = AL11.alGetError();
			if(error != 0) {
				System.out.println("[ERROR] " + error);
			}
			
			// check playing
			SourceState state = streamSpeaker.getState();
			if(state == SourceState.STOPPED || state == SourceState.INITIAL) {
				streamSpeaker.play();
				System.out.println("[TRY PLAY]");
			}
			
//			System.out.println(streamSpeaker.getState());
			
			// print timer
			System.out.println("[Loop time] " + (System.currentTimeMillis() - startTimestamp));
			
			// check exit
			if(bufferedCount > endBufferCount) break;
			
		}
		
		// Release Driver
		driver.releaseSpeaker(streamSpeaker);
		driver.release();
		
		// Release Memory Pool
		try {
			pool.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
