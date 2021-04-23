package xueli.craftgame.renderer.model;

import org.lwjgl.util.vector.Vector3f;

public class Cube {
	
	public static final Cube FULL_CUBE = new Cube(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));

	private Vector3f from, to;

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
