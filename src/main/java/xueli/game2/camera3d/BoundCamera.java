package xueli.game2.camera3d;

import org.lwjgl.utils.vector.Matrix4f;

public class BoundCamera implements ICamera {

	private ICamera camera;

	public BoundCamera(ICamera camera) {
		this.camera = camera;
	}

	@Override
	public Matrix4f getCameraMatrix() {
		if(camera == null) {
			Matrix4f matrix = new Matrix4f();
			matrix.setIdentity();
			return matrix;
		}
		return camera.getCameraMatrix();
	}

	public void setCamera(ICamera camera) {
		this.camera = camera;
	}

}
