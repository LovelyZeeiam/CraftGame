package xueli.gamengine.musicjson.nbs;

public class NoteBlock {

	private short tick, layer;
	private byte key;
	private String inst;

	public NoteBlock(short tick, short layer, byte inst, byte key) {
		this.tick = tick;
		this.layer = layer;
		this.key = key;
		this.inst = convertToString(inst);

	}

	private String convertToString(byte inst) {
		switch (inst) {
		default:
			return "harp";
		case 1:
			return "dbass";
		case 2:
			return "bdrum";
		case 3:
			return "sdrum";
		case 4:
			return "click";
		case 5:
			return "guitar";
		case 6:
			return "flute";
		case 7:
			return "bell";
		case 8:
			return "icechime";
		case 9:
			return "xylobone";
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

	public short getTick() {
		return tick;
	}

	public short getLayer() {
		return layer;
	}

	public String getInst() {
		return inst;
	}

	public byte getKey() {
		return key;
	}

}
