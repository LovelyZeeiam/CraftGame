package xueli.musicjson.nbs;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NBSInputStream extends DataInputStream {

	private final short songLength;
	private final short songHeight;
	private final String name;
	private final String author;
	private final String originAuthor;
	private final String desc;
	private final short tempo;
	private final byte autoSaving;
	private final byte autoSavingDuration;
	private final byte timeSign;
	private final int minSpent;
	private final int leftClicks;
	private final int rightClicks;
	private final int blocksAdded;
	private final int blocksRemoved;
	private final String filename;
	private final boolean loopOn;
	private final byte maxLoopCount;
	private final short loopStartTick;

	public NBSInputStream(InputStream in) throws IOException {
		super(in);

		readInt();

		songLength = Short.reverseBytes(readShort());
		songHeight = Short.reverseBytes(readShort());
		name = readString();
		author = readString();
		originAuthor = readString();
		desc = readString();
		tempo = Short.reverseBytes(readShort());
		autoSaving = readByte();
		autoSavingDuration = readByte();
		timeSign = readByte();
		minSpent = Integer.reverseBytes(readInt());
		leftClicks = Integer.reverseBytes(readInt());
		rightClicks = Integer.reverseBytes(readInt());
		blocksAdded = Integer.reverseBytes(readInt());
		blocksRemoved = Integer.reverseBytes(readInt());
		filename = readString();
		loopOn = readBoolean();
		maxLoopCount = readByte();
		loopStartTick = Short.reverseBytes(readShort());

	}

	public List<NoteBlock> readNoteBlocks() throws IOException {
		ArrayList<NoteBlock> blocks = new ArrayList<>();

		short maxlayer = 0;

		short tick = -1;
		short jumps = 0;
		while (true) {
			jumps = Short.reverseBytes(readShort());
			if (jumps == 0)
				break;
			tick += jumps;
			short layer = -1;
			while (true) {
				jumps = Short.reverseBytes(readShort());
				if (jumps == 0)
					break;
				layer += jumps;

				byte inst = readByte();
				byte key = readByte();
				byte velocity = readByte();
				byte panning = readByte();
				short pitch = Short.reverseBytes(readShort());

				if (layer > maxlayer)
					maxlayer = layer;

				blocks.add(new NoteBlock((short) (tick + 10), layer, inst, key, (float) velocity / 100.0f));

			}
		}

		// layers
		Layer[] layers = new Layer[maxlayer + 1];
		for (int i = 0; i < layers.length; i++) {
			String name = readString();
			boolean locked = readBoolean();
			float volume = readByte() / 100.0f;
			float panning = (readByte() - 100.0f) / 100.0f;
			layers[i] = new Layer(i, name, locked, volume, panning);

		}

		blocks.forEach(b -> b.setVolume(b.getVolume() * layers[b.getLayer()].getVolume()));

		return blocks;
	}

	private String readString() throws IOException {
		int length = Integer.reverseBytes(readInt());
		byte[] strBytes = new byte[length];
		read(strBytes, 0, length);
		String str = new String(strBytes);
		return str;
	}

	public short getSongLength() {
		return songLength;
	}

	public short getSongHeight() {
		return songHeight;
	}

	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	public String getOriginAuthor() {
		return originAuthor;
	}

	public String getDesc() {
		return desc;
	}

	public short getTempo() {
		return tempo;
	}

	public byte getAutoSaving() {
		return autoSaving;
	}

	public byte getAutoSavingDuration() {
		return autoSavingDuration;
	}

	public byte getTimeSign() {
		return timeSign;
	}

	public int getMinSpent() {
		return minSpent;
	}

	public int getLeftClicks() {
		return leftClicks;
	}

	public int getRightClicks() {
		return rightClicks;
	}

	public int getBlocksAdded() {
		return blocksAdded;
	}

	public int getBlocksRemoved() {
		return blocksRemoved;
	}

	public String getFilename() {
		return filename;
	}

	public boolean isLoopOn() {
		return loopOn;
	}

	public byte getMaxLoopCount() {
		return maxLoopCount;
	}

	public short getLoopStartTick() {
		return loopStartTick;
	}

}
