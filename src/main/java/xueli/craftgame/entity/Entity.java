package xueli.craftgame.entity;

import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.vector.Vector;

public abstract class Entity {

	public Vector pos;
	
	// TODO: For entity bones
	
	
	Vector3f speed = new Vector3f();
	public Vector3f acceleration = new Vector3f();
	// 实体所在的世界
	protected World world;
	
	public boolean isOnGround = true;

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
	
	protected void gravity() {
		if(!isOnGround) {
			// TODO
			
		}
		
	}

	protected void updatePos() {
		isOnGround = false;
		
		speed.x += acceleration.x * Time.deltaTime / 1000.0f;
		speed.y += acceleration.y * Time.deltaTime / 1000.0f;
		speed.z += acceleration.z * Time.deltaTime / 1000.0f;
		
		Vector3f deltaPos = new Vector3f(speed.x * Time.deltaTime / 1000.0f, speed.y * Time.deltaTime / 1000.0f, speed.z * Time.deltaTime / 1000.0f);
		
		/*
		 * pos.x += deltaPos.x; pos.y += deltaPos.y; pos.z += deltaPos.z;
		 */
		collider.entityCollide(this, deltaPos);
		
		speed.x *= 0.8f;
		speed.y *= 0.8f;
		speed.z *= 0.8f;
		
		acceleration.x = acceleration.y = acceleration.z = 0;
		
	}

	public abstract void tick();

	public boolean doCollide() {
		return true;
	}

	public abstract float getSpeed();

	public abstract AABB getOriginAABB();

}
