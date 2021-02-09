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
			speed.y -= 0.4f * Time.deltaTime;
			
			if(speed.y <= -5.0f)
				speed.y = -5.0f;
			
		}
		
	}
	
	// 不管因为什么原因 达到200的y都会抑制y更新

	protected void updatePos() {
		isOnGround = false;
		
		Vector3f deltaPos = new Vector3f(speed.x * Time.deltaTime / 1000.0f, speed.y * Time.deltaTime / 1000.0f, speed.z * Time.deltaTime / 1000.0f);
		
		/*
		 * pos.x += deltaPos.x; pos.y += deltaPos.y; pos.z += deltaPos.z;
		 */
		collider.entityCollide(this, deltaPos);

		speed.x *= 0.1f;
		speed.y *= 0.1f;
		speed.z *= 0.1f;
		
	}

	public abstract void tick();

	public boolean doCollide() {
		return true;
	}

	public abstract float getSpeed();

	public abstract AABB getOriginAABB();

}
