package xueLi.craftgame.entity;

import org.lwjgl.util.vector.Vector3f;

import xueLi.craftgame.world.World;
import xueLi.gamengine.utils.Time;
import xueLi.gamengine.utils.Vector;

public abstract class Entity {

	public Vector pos;
	public boolean isInLiquid = false;

	// TODO: For entity bones

	public Entity(float x, float y, float z) {
		pos = new Vector(x, y, z);

	}

	public Entity(float x, float y, float z, float rotX, float rotY, float rotZ) {
		pos = new Vector(x, y, z, rotX, rotY, rotZ);

	}

	public Vector3f speed = new Vector3f();

	public void updatePos(World w) {
		Vector3f deltaPos = new Vector3f(speed.x * Time.deltaTime, speed.y * Time.deltaTime, speed.z * Time.deltaTime);

		pos.x += deltaPos.x;
		pos.y += deltaPos.y;
		pos.z += deltaPos.z;

	}

	public abstract void tick(World world);

	public abstract float getSpeed();

}
