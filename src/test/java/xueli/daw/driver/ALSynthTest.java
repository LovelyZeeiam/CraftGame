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
import xueli.utils.buffer.BufferPool;
import xueli.utils.buffer.BufferPoolListener;
import xueli.utils.buffer.LotsOfByteBuffer;
import xueli.utils.buffer.MemoryHandler;

// It is LovelyZeeiam's first Synthesizer and buffered streaming note player!!!
public class ALSynthTest {
	
	public static final int SAMPLE_RATE = 44100;
	public static final double PER_SAMPLE_LENGTH = 1.0 / SAMPLE_RATE;
	public static final int BUFFER_SAMPLE_COUNT = 8192;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// Read Note File
		var nbsIn = new NBSInputStream(new FileInputStream("res/music/ni_ting_de_dao.nbs"));
		List<NoteBlock> notes = nbsIn.readNoteBlocks();
		double tickPerSecond = nbsIn.getTempo() / 100.0;
		double musicLength = nbsIn.getSongLength() / tickPerSecond;
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
		final int endBufferCount = (int) ((musicLength + 5) * SAMPLE_RATE / BUFFER_SAMPLE_COUNT);
		int bufferedCount = 0;
		double bufferTime = 0.0;
		
		while(true) {
//			long startTimestamp = System.currentTimeMillis();
			
			// free used buffer
			{
				int usedBufferCount = streamSpeaker.queryProcessedBufferCount();
//				if(usedBufferCount != 0) {
//					System.out.println(usedBufferCount);
//				}
				var usedBuffers = new ALBuffer[usedBufferCount];
				streamSpeaker.unqueueBuffer(usedBuffers);
				
				for(var buf : usedBuffers) {
					if(buf == null) continue;
					var handler = bufferHandlerMap.remove(buf);
					handler.release();
//					System.out.println("[FREE] " + handler.toString());
				}
				
			}
			
//			if(pool.getFreeCount() != 0) {
//				System.out.println(pool.getAllocatedCount() + ", " + pool.getFreeCount());
//			}
			
//			System.out.println(pool.getAllocatedCount() + ", " + pool.getFreeCount());
			
			// store buffer
			while(pool.getAllocatedCount() < 20) {
				var handler = pool.spare();
//				System.out.println("[ALLOC] " + handler.toString());
				var memory = handler.getBuffer();
				var alBuffer = bufferMap.get(memory);
				bufferHandlerMap.put(alBuffer, handler);
				
				if(bufferedCount % 10 == 0) {
					System.out.println("==== [BufferTime] " + bufferedCount + "," + bufferTime);	
				}
				
				for(int i = 0; i < BUFFER_SAMPLE_COUNT; i++) {
					bufferTime = (bufferedCount * BUFFER_SAMPLE_COUNT + i) * PER_SAMPLE_LENGTH;
					
					// do sequencer
					{
						sequencer.progress((int)(bufferTime * 1000), sequencerDest);
						for(var event : sequencerDest) {
							System.out.println("[note] " + event.toString());
							playingNotes.add(new PlayingNote(bufferTime, event));
						}
						sequencerDest.clear();
						
					}
					
					// write buffer
					{
						double thisValue = 0.0;
						
						var noteIterator = playingNotes.iterator();
						while(noteIterator.hasNext()) {
							var note = noteIterator.next();
							double sustain = bufferTime - note.startTime;
							if(sustain > 1.0) noteIterator.remove();
//							System.out.println(sustain);
							
							double frequency = (440.0 / 32.0) * Math.pow(2, (note.block.getKey() + 24 - 9.0) / 12.0);
//							System.out.println(frequency);
							double originValue = 0.4 * SynthesizerUtils.triangle(frequency, sustain) * (1.0 - sustain);
							thisValue += originValue;
							
						}
						
						if(thisValue > 1.0) thisValue = 1.0;
						if(thisValue < -1.0) thisValue = -1.0;
						
						memory.putShort((short) (thisValue * 32767.0));
					}
					
				}
				
				bufferedCount++;
				
				memory.setReadWrite(true);
				alBuffer.setBuffer(memory, BufferFormat.MONO16, SAMPLE_RATE);
				alBuffer.doingSyncIfNecessary();
				streamSpeaker.queueBuffer(alBuffer);
				
			}
			
			int error = AL11.alGetError();
			if(error != 0) {
				System.out.println("[ERROR] " + error);
			}
			
			// check playing
			SourceState state = streamSpeaker.getState();
			if(state == SourceState.STOPPED || state == SourceState.INITIAL) {
				streamSpeaker.play();
			}
			
			// check exit
			if(bufferedCount > endBufferCount) break;
			
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
//			System.out.println(bufferedCount + ", " + endBufferCount);
			
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
