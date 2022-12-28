package org.lwjgl.utils.vector;

import java.util.Objects;

public class Vector2i {

	public int x, y;

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

}
