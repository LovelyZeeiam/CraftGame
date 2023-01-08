package xueli.jrich2;

import java.util.Objects;

public class Color {
	
	private int r, g, b;
	
	public Color(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;	
	}
	
	public Color(float r, float g, float b) {
		this.r = (int) (r * 255);
		this.g = (int) (g * 255);
		this.b = (int) (b * 255);
	}
	
	public int getR() {
		return r;
	}
	
	public int getG() {
		return g;
	}
	
	public int getB() {
		return b;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(b, g, r);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Color other = (Color) obj;
		return b == other.b && g == other.g && r == other.r;
	}

	@Override
	public String toString() {
		return "Color [r=" + r + ", g=" + g + ", b=" + b + "]";
	}
	
}
