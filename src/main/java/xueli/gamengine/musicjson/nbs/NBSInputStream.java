package xueli.gamengine.musicjson.nbs;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NBSInputStream extends DataInputStream {

	private short songLength;
	private short songHeight;
	private String name, author, originAuthor, desc;
	private short tempo;
	private byte autoSaving;
	private byte autoSavingDuration;
	private byte timeSign;
	private int minSpent, leftClicks, rightClicks, blocksAdded, blocksRemoved;
	private String filename;
	private boolean loopOn;
	private byte maxLoopCount;
	private short loopStartTick;

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

				blocks.add(new NoteBlock(tick, layer, inst, key));

			}
		}

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
