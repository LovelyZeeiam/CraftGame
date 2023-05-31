package xueli.game2.phys.aabb;

import org.lwjgl.utils.vector.Vector3i;

public enum BoxFace {

	X_MINUS(new Vector3i(-1, 0, 0)), X_PLUS(new Vector3i(1, 0, 0)), Y_MINUS(new Vector3i(0, -1, 0)),
	Y_PLUS(new Vector3i(0, 1, 0)), Z_MINUS(new Vector3i(0, 0, -1)), Z_PLUS(new Vector3i(0, 0, 1));

	private Vector3i faceTo;

	private BoxFace(Vector3i faceTo) {
		this.faceTo = faceTo;
	}

	public Vector3i getFaceToVector() {
		return faceTo;
	}

}
