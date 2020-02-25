package xueLi.craftGame.utils;

import org.lwjgl.util.vector.Vector3f;

public class HitBox {

	public float x1, y1, z1, x2, y2, z2;

	public HitBox(float x1, float y1, float z1, float x2, float y2, float z2) {
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
	}

	public boolean isCollide(HitBox h2) {
		if (isPointIn(x1, y1, z1))
			return true;
		if (isPointIn(x1, y1, z2))
			return true;
		if (isPointIn(x1, y2, z1))
			return true;
		if (isPointIn(x1, y2, z2))
			return true;
		if (isPointIn(x2, y1, z1))
			return true;
		if (isPointIn(x2, y1, z2))
			return true;
		if (isPointIn(x2, y2, z1))
			return true;
		if (isPointIn(x2, y2, z2))
			return true;
		return false;
	}

	public boolean isPointIn(float x, float y, float z) {
		return isPointIn(new Vector3f(x, y, z), this);
	}

	public static boolean isPointIn(Vector3f point, HitBox box) {
		if (point.x <= box.x1 || point.x >= box.x2)
			return false;
		if (point.y >= box.y2 || point.y <= box.y1)
			return false;
		if (point.z <= box.z1 || point.z >= box.z2)
			return false;
		return true;
	}

	public HitBox move(float x, float y, float z) {
		return new HitBox(x1 + x, y1 + y, z1 + z, x2 + x, y2 + y, z2 + z);
	}
	
	@Override
	public String toString() {
		return x1 + "," + y1 + "," + z1 + " " + x2 + "," + y2 + "," + z2;
	}

}
