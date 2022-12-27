package xueli.game2.camera3d;

import org.lwjgl.utils.vector.Matrix4f;

public class BoundCamera implements ICamera {

	private final BoundCamera camera;

	public BoundCamera(BoundCamera camera) {
		this.camera = camera;
	}

	@Override
	public Matrix4f getCameraMatrix() {
		return camera.getCameraMatrix();
	}

}
