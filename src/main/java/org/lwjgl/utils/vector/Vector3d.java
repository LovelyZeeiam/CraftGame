package org.lwjgl.utils.vector;

import java.util.Objects;

public class Vector3d {

	public double x, y, z;

	public Vector3d() {
		this.x = this.y = this.z = 0;
	}

	public Vector3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void normalize() {
		double length = Math.sqrt(x * x + y * y + z * z);
		this.x /= length;
		this.y /= length;
		this.z /= length;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vector3d vector3d = (Vector3d) o;
		return Double.compare(vector3d.x, x) == 0 && Double.compare(vector3d.y, y) == 0 && Double.compare(vector3d.z, z) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public String toString() {
		return "Vector3d{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}

	public static Vector3d add(Vector3d v1, Vector3d v2) {
		return new Vector3d(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
	}

}
