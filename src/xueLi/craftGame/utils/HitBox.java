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
	
	public HitBox expand(float x,float y,float z) {
		if(x < 0)
			this.x1 += x;
		else if(x > 0)
			this.x2 += x;
		if(y < 0)
			this.y1 += y;
		else if(y > 0)
			this.y2 += y;
		if(z < 0)
			this.z1 += z;
		else if(z > 0)
			this.z2 += z;
		return this;
	}
	
	public float xCollide(HitBox a,float x) {
		if(a.y2 <= this.y1 || a.y1 >= this.y2)
			return x;
		if(a.z2 <= this.z1 || a.z1 >= this.z2)
			return x;
		float max;
		if(x > 0.0f && a.x2 <= this.x1) {
			max = this.x1 - a.x2;
			if(max < x)
				x = max;
		}else if(x < 0.0f && a.x1 >= this.x2) {
			max = this.x2 - a.x1;
			if(max > x)
				x = max;
		}
		return x;
	}
	
	public float yCollide(HitBox a,float y) {
		if(a.x2 <= this.x1 || a.x1 >= this.x2)
			return y;
		if(a.z2 <= this.z1 || a.z1 >= this.z2)
			return y;
		float max;
		if(y > 0.0f && a.y2 <= this.y1) {
			max = this.y1 - a.y2;
			if(max < y)
				y = max;
		}else if(y < 0.0f && a.y1 >= this.y2) {
			max = this.y2 - a.y1;
			if(max > y)
				y = max;
		}
		return y;
	}

	public float zCollide(HitBox a,float z) {
		if(a.x2 <= this.x1 || a.x1 >= this.x2)
			return z;
		if(a.y2 <= this.y1 || a.y1 >= this.y2)
			return z;
		float max;
		if(z > 0.0f && a.z2 <= this.z1) {
			max = this.z1 - a.z2;
			if(max < z)
				z = max;
		}else if(z < 0.0f && a.z1 >= this.z2) {
			max = this.z2 - a.z1;
			if(max > z)
				z = max;
		}
		return z;
	}
	
	@Override
	public String toString() {
		return x1 + "," + y1 + "," + z1 + " " + x2 + "," + y2 + "," + z2;
	}

}
