package org.lwjgl.utils.vector;

import java.util.Objects;

public class Vector3b {

	public byte x, y, z;

	public Vector3b(byte x, byte y, byte z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public byte getX() {
		return x;
	}

	public void setX(byte x) {
		this.x = x;
	}

	public byte getY() {
		return y;
	}

	public void setY(byte y) {
		this.y = y;
	}

	public byte getZ() {
		return z;
	}

	public void setZ(byte z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "Vector3b [x=" + x + ", y=" + y + ", z=" + z + "]";
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
		Vector3b other = (Vector3b) obj;
		return x == other.x && y == other.y && z == other.z;
	}

}
