package xueli.craftgame.model;

import org.lwjgl.utils.vector.Vector3f;

public class Cube {

	protected Vector3f from, to;

	public Cube(Vector3f from, Vector3f to) {
		this.from = from;
		this.to = to;

	}

	public Vector3f getFrom() {
		return from;
	}

	public Vector3f getTo() {
		return to;
	}

}
