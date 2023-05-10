package xueli.game2.camera3d;

import org.lwjgl.utils.vector.Matrix4f;
import org.lwjgl.utils.vector.Vector3f;

import xueli.game2.Vector;

public class MovableCamera extends Vector implements ICamera {
	
	private static final long serialVersionUID = 5715380559084534935L;

	public MovableCamera() {
		super();
	}

	public MovableCamera(float x, float y, float z) {
		super(x, y, z);
	}

	public MovableCamera(float x, float y, float z, float rotX, float rotY, float rotZ) {
		super(x, y, z, rotX, rotY, rotZ);
	}

	public MovableCamera(Vector other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
		this.rotX = other.rotX;
		this.rotY = other.rotY;
		this.rotZ = other.rotZ;
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
