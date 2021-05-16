package xueli.musicjson.nbs;

public class NoteBlock {

	private short tick, layer;
	private byte key;
	private String inst;
	private float velocity;
	private float panning;
	private float pitch;
	
	public NoteBlock(short tick, short layer, byte inst, byte key, byte velocity, byte panning, short pitch) {
		this.tick = tick;
		this.layer = layer;
		this.key = key;
		this.inst = convertToString(inst);
		this.velocity = velocity / 100.0f;
		this.panning = panning / 100.0f;
		this.pitch = pitch / 100.0f;
		
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

	public float getVelocity() {
		return velocity;
	}

	public float getPanning() {
		return panning;
	}
	
	public float getPitch() {
		return pitch;
	}

}