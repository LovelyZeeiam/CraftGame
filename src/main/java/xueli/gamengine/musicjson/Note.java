package xueli.gamengine.musicjson;

public class Note extends INoteCommand {

	private String type;
	private float rate;

	public Note(String type, float rate) {
		this.type = type;
		this.rate = rate;
	}

	public String getType() {
		return type;
	}

	public float getRate() {
		return rate;
	}
}
