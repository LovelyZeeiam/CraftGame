package xueli.craftgame.entity;

import org.lwjgl.util.vector.Vector3f;
import xueli.craftgame.world.CubeWorldCollider;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.Vector;

public abstract class Entity {

	public Vector pos;
	// TODO: For entity bones
	public Vector3f speed = new Vector3f();
	// 实体所在的世界
	protected World world;

	private CubeWorldCollider collider;

	public Entity(float x, float y, float z, World world) {
		pos = new Vector(x, y, z);
		this.world = world;
		collider = new CubeWorldCollider(world);

	}

	public Entity(float x, float y, float z, float rotX, float rotY, float rotZ, World world) {
		pos = new Vector(x, y, z, rotX, rotY, rotZ);
		this.world = world;
		collider = new CubeWorldCollider(world);

	}

	public void updatePos() {
		Vector3f deltaPos = new Vector3f(speed.x * Time.deltaTime, speed.y * Time.deltaTime, speed.z * Time.deltaTime);
		pos.x += deltaPos.x;
		pos.y += deltaPos.y;
		pos.z += deltaPos.z;

	}

	public abstract void tick();

	public boolean doCollide() {
		return true;
	}

	public abstract float getSpeed();

	public abstract AABB getOriginAABB();

}
