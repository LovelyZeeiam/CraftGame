package xueli.jrich;

import java.util.Objects;

record ConsolePosition(int x, int y) {

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ConsolePosition that = (ConsolePosition) o;
		return x == that.x && y == that.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

}
