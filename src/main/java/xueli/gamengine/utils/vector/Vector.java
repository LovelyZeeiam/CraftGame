package xueli.gamengine.utils.vector;

import java.io.Serializable;

public class Vector implements Serializable {

	private static final long serialVersionUID = 1957284979693368762L;

	public float x;
	public float y;
	public float z;
	public float rotX, rotY, rotZ;

	public Vector() {
		x = y = z = rotX = rotY = rotZ = 0;
	}

	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		rotX = rotY = rotZ = 0;
	}

	public Vector(float x, float y, float z, float rotX, float rotY, float rotZ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}

	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + ", z=" + z + ", rotX=" + rotX + ", rotY=" + rotY + ", rotZ=" + rotZ
				+ "]";
	}

}
