package xueLi.craftGame.entity;

import xueLi.craftGame.utils.Vector;

public abstract class Entity {

	public Vector pos;

	public Entity(float x, float y, float z) {
		pos = new Vector(x, y, z);

	}

	public Entity(float x, float y, float z, float rotX, float rotY, float rotZ) {
		pos = new Vector(x, y, z, rotX, rotY, rotZ);

	}

	public void increasePosition(float x, float y, float z, float rotX, float rotY, float rotZ) {
		pos.x += x;
		pos.y += y;
		pos.z += z;
		pos.rotX += rotX;
		pos.rotY += rotY;
		pos.rotZ += rotZ;

		if (pos.rotY > 360)
			pos.rotY -= 360;
		if (pos.rotX > 90)
			pos.rotX = 90;
		if (pos.rotX < -90)
			pos.rotX = -90;
		if (pos.rotZ > 360)
			pos.rotZ -= 360;

	}

	public abstract void tick();

	public abstract float getSpeed();

}
