package xueli.daw.driver;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import xueli.daw.nbs.NBSInputStream;
import xueli.daw.nbs.NBSSequencer;
import xueli.daw.nbs.NoteBlock;
import xueli.daw.synthesizer.SynthesizerUtils;

// Give the world a little wrecking ball from this failed synthesizer!
@Deprecated
//@SuppressWarnings("unused")
public class EasySynthesizerTest {

	public static float SAMPLE_RATE = 44100;

	public static void main(String[] args) throws InvalidMidiDataException, IOException, LineUnavailableException {
//		// Create Context
//		var context = new DawContext();
//		var channelManager = context.getChannelManager();
//		
//		// Create channel
//		var channel = new Channel(context);
//		channel.setGenerator(new SineWaveSynthesizer());
//		channelManager.addChannel(channel);
//		
//		// Run tick
//		long startTime = System.currentTimeMillis();
//		while(System.currentTimeMillis() - startTime < 1000) {
//			long t1 = System.currentTimeMillis();
//			context.tick();
//			System.out.println(System.currentTimeMillis() - t1);
//			
//		}
//		
//		// Release resources
//		context.release();
		
		// Read Note Blocks
		NBSInputStream in = new NBSInputStream(new FileInputStream("res/music/lucky_star.nbs"));
		List<NoteBlock> noteBlocks = in.readNoteBlocks();
		double tickPerSecond = in.getTempo() / 100.0;
		long microsecondLength = (long) (in.getSongLength() / tickPerSecond * 1000.0);
		in.close();
		
		// Open Audio Line
		AudioFormat af = new AudioFormat(SAMPLE_RATE, 16, 1, true, true);
		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);

		sdl.open(af);
		sdl.start();
		
		// Play the sound
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new JavaSoundOutputStream(sdl));
		@SuppressWarnings("resource") DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
		
		class Note {
			NoteBlock message;
			long startTime;
		}
		ArrayList<Note> playingNote = new ArrayList<>();
		
		NBSSequencer sequencer = new NBSSequencer(tickPerSecond, noteBlocks);
		long startTime = System.currentTimeMillis();
		ArrayList<NoteBlock> temp = new ArrayList<>();
		
		double blend = 0.0;
		
		double sample = 0.0;
		long time, lasttime = 0;
		while((time = System.currentTimeMillis() - startTime) < (microsecondLength + 1000)) {
			temp.clear();
			sequencer.read(temp);
			for(NoteBlock m : temp) {
				Note note = new Note();
				note.message = m; 
				note.startTime = time;
				playingNote.add(note);
			}
			
			var noteIterator = playingNote.iterator();
			double value = 0.0;
			
			while(noteIterator.hasNext()) {
				var current = noteIterator.next();
				long sustain = time - current.startTime;
				if(sustain > 1000) noteIterator.remove();
				
				double frequency = (440.0 / 32.0) * Math.pow(2, (current.message.getKey() + 8 - 9.0) / 12.0);
				
				double originValue = SynthesizerUtils.sine(frequency, sustain / 1000.0) * (1000 - sustain) / 1000.0;
//				System.out.println(sustain);
				if(value == 0) value = originValue;
				else value = (originValue + value) / 1.2;
				
			}
			
			if(blend == 0.0) blend = value;
			else blend = (blend + value) / 2.0;
			
			sample += (double) (time - lasttime) / 1000.0 * SAMPLE_RATE;
			boolean doSample = false;
			while(sample > 1) {
				System.out.println(blend);
				dataOutputStream.writeShort((int) (blend * 20000.0));
				doSample = true;
				sample--;
			}
			if(doSample) {
				blend = 0.0;
			}
			
//			System.out.println("=======");
			
//			System.out.println(value);
			
			lasttime = time;
			
		}
		
		// Close the line
		bufferedOutputStream.flush();
		sdl.drain();
		
		sdl.stop();
		sdl.close();

	}
	
//	public static class MidiSequencer {
//		
//		private final Sequence sequence;
//		private final long startTime;
//		
//		private final TreeMap<Long, ArrayList<MidiMessage>> timeline = new TreeMap<>();
//		
//		public MidiSequencer(Sequence sequence) {
//			this.sequence = sequence;
//			this.buildTimeline();
//			this.startTime = System.currentTimeMillis();
//			
//		}
//		
//		private void buildTimeline() {
//			Track[] tracks = sequence.getTracks();
//			for(var track : tracks) {
//				int size = track.size();
//				for(int i = 0; i < size; i++) {
//					var event = track.get(i);
//					this.addTimelineItem(com.sun.media.sound.MidiUtils.tick2microsecond(sequence, event.getTick(), null), event.getMessage());
//				}
//			}
//			
//		}
//		
//		private void addTimelineItem(long time, MidiMessage item) {
//			timeline.computeIfAbsent(time, t -> new ArrayList<>()).add(item);
//		}
//		
//		private long lastTime = 0;
//		
//		// The return value indicated whether the music reaches the end.
//		public boolean read(List<MidiMessage> dest) {
//			long currentTime = System.currentTimeMillis() - this.startTime;
//			
//			Long lowerKey = currentTime;
//			boolean exists = false;
//			while((lowerKey = this.timeline.lowerKey(lowerKey)) != null) {
//				if(lowerKey.longValue() < lastTime) break;
//				exists = true;
//				dest.addAll(timeline.get(lowerKey));
//			}
//			
//			return exists;
//		}
//		
//	}

}
