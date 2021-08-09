package xueli.musicjson.nbs;

public class NoteBlock {

	private final short tick;
	private final short layer;
	private final byte key;
	private final byte inst;
	private float volume;

	public NoteBlock(short tick, short layer, byte inst, byte key, float volume) {
		this.tick = tick;
		this.layer = layer;
		this.key = key;
		this.inst = inst;
		this.volume = volume;

	}

	void setVolume(float volume) {
		this.volume = volume;
	}

	public short getTick() {
		return tick;
	}

	public short getLayer() {
		return layer;
	}

	public byte getInst() {
		return inst;
	}

	public byte getKey() {
		return key;
	}

	public String getStringFromNoteType() {
		switch (inst) {
		default:
			return "harp";
		case 1:
			return "bassattack";
		case 2:
			return "bd";
		case 3:
			return "snare";
		case 4:
			return "hat";
		case 5:
			return "guitar";
		case 6:
			return "flute";
		case 7:
			return "bell";
		case 8:
			return "chime";
		case 9:
			return "xylophone";
		case 10:
			return "iron_xylophone";
		case 11:
			return "cow_bell";
		case 12:
			return "didgeridoo";
		case 13:
			return "bit";
		case 14:
			return "banjo";
		case 15:
			return "pling";
		}
	}

	@Override
	public String toString() {
		return "NoteBlock{" + "tick=" + tick + ", layer=" + layer + ", key=" + key + ", inst='" + inst + '\'' + '}';
	}

	public float getVolume() {
		return volume;
	}

}
