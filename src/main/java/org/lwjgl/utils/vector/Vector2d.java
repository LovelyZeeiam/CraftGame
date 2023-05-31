package org.lwjgl.utils.vector;

import java.util.Objects;

public class Vector2d {

	public double x, y;

	public Vector2d() {
		this.x = this.y = 0;
	}

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Vector2d vector2d = (Vector2d) o;
		return Double.compare(vector2d.x, x) == 0 && Double.compare(vector2d.y, y) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return "Vector2d{" + "x=" + x + ", y=" + y + '}';
	}

	public static Vector2d add(Vector2d v1, Vector2d v2) {
		return new Vector2d(v1.x + v2.x, v1.y + v2.y);
	}

}
