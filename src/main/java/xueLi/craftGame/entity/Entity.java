package xueLi.craftGame.entity;

import org.lwjgl.util.vector.Vector3f;

import xueLi.craftGame.utils.DisplayManager;
import xueLi.craftGame.utils.Vector;
import xueLi.craftGame.world.World;

public abstract class Entity {

	public Vector pos;
	public boolean isInLiquid = false;

	// For physical engine
	// public boolean[] collide = new boolean[6];

	//TODO: For entity bones

	public Entity(float x, float y, float z) {
		pos = new Vector(x, y, z);

	}

	public Entity(float x, float y, float z, float rotX, float rotY, float rotZ) {
		pos = new Vector(x, y, z, rotX, rotY, rotZ);

	}

	public Vector3f force = new Vector3f();
	public Vector3f speed = new Vector3f();

	// this is for real physical engine and I haven't done yet :)
	// I think maybe I can figure out how to correct the position of an entity by
	// the direction of the speed.
	// But now one of the most important things is adding entities,so :} I think it
	// will be done much later.
	public void updatePos(World w) {
		Vector3f deltaPos = new Vector3f(speed.x * DisplayManager.deltaTime, speed.y * DisplayManager.deltaTime,
				speed.z * DisplayManager.deltaTime);

		/**
		 * The player will be stuck when collided with this code
		 */
		// HitBox box = getHitBox();
		// if(w.getHitBoxes(box.move(deltaPos.x + deltaPos.x > 0 ? 0.005f : -0.005f,
		// deltaPos.y + deltaPos.y > 0 ? 0.005f : -0.005f, deltaPos.z + deltaPos.z > 0 ?
		// 0.005f : -0.005f), w.wlimit_long).size() == 0) {
		pos.x += deltaPos.x;
		pos.y += deltaPos.y;
		pos.z += deltaPos.z;
		// pos.y = new
		// BigDecimal(pos.y).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
		// }

		/**
		 * So I can only write a false engine code like this
		 */
		// if (pos.y + this.getOriginHitBox().y1 < 5)
		// pos.y = 5 - this.getOriginHitBox().y1;

		// Physical? No no no it will be much later :}
		force.set(0, 0, 0);
		// At least moving had become smoother :)

	}

	public abstract void tick(World world);

	public abstract float getSpeed();

	public abstract HitBox getOriginHitBox();

	protected HitBox getHitBox() {
		return getOriginHitBox().move(pos.x, pos.y, pos.z);
	}

}
