package xueli.game2.camera3d;

import org.lwjgl.utils.vector.Matrix4f;
import org.lwjgl.utils.vector.Vector3f;
import xueli.game.vector.Vector;

public class MovableCamera extends Vector implements ICamera {

	public MovableCamera() {
		super();
	}

	public MovableCamera(float x, float y, float z) {
		super(x, y, z);
	}

	public MovableCamera(float x, float y, float z, float rotX, float rotY, float rotZ) {
		super(x, y, z, rotX, rotY, rotZ);
	}

	@Override
	public Matrix4f getCameraMatrix() {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(-this.rotX), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(this.rotY), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(this.rotZ), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
		Vector3f negativeCamPos = new Vector3f((float) -this.x, (float) -this.y, (float) -this.z);
		Matrix4f.translate(negativeCamPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}

}
