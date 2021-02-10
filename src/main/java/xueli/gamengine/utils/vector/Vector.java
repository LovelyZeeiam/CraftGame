package xueli.gamengine.utils.vector;

public class Vector {

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

}
