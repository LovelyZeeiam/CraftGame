package org.lwjgl.utils.vector;

import java.util.Objects;

public class Vector3i {

	public int x, y, z;

	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3i(Vector3d doubleVector) {
		this.x = (int) Math.floor(doubleVector.x);
		this.y = (int) Math.floor(doubleVector.y);
		this.z = (int) Math.floor(doubleVector.z);

	}

	public Vector3i(Vector3f floatVector) {
		this.x = (int) Math.floor(floatVector.x);
		this.y = (int) Math.floor(floatVector.y);
		this.z = (int) Math.floor(floatVector.z);

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	@Override
	public String toString() {
		return "Vector3i [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector3i other = (Vector3i) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	public static Vector3i add(Vector3i v1, Vector3i v2) {
		return new Vector3i(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
	}

}
