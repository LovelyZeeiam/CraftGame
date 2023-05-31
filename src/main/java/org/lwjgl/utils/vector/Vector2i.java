package org.lwjgl.utils.vector;

import java.util.Objects;

public class Vector2i implements Comparable<Vector2i> {

	public int x, y;

	public Vector2i() {
		this.x = this.y = 0;
	}

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Vector2i{" + "x=" + x + ", y=" + y + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Vector2i vector2i = (Vector2i) o;
		return x == vector2i.x && y == vector2i.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public int compareTo(Vector2i o) {
		if (o == null)
			return this.hashCode();
		return this.hashCode() - o.hashCode();
	}

}
