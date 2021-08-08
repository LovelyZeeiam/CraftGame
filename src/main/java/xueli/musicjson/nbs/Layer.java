package xueli.musicjson.nbs;

public class Layer {

	private int layerId;
	private String name;
	private boolean isLocked;
	private float volume;
	private float panning;

	public Layer(int layerId, String name, boolean isLocked, float volume, float panning) {
		this.layerId = layerId;
		this.isLocked = isLocked;
		this.volume = volume;
		this.panning = panning;
		this.name = name;

	}

	public int getLayerId() {
		return layerId;
	}

	public String getName() {
		return name;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public float getVolume() {
		return volume;
	}

	public float getPanning() {
		return panning;
	}

}
