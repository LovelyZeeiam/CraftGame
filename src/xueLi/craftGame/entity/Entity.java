package xueLi.craftGame.entity;

import org.lwjgl.util.vector.Vector3f;

import xueLi.craftGame.block.Block;
import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.utils.HitBox;
import xueLi.craftGame.utils.Vector;
import xueLi.craftGame.world.Chunk;

public abstract class Entity {

	public Vector pos;
	public boolean isOnGround = false;

	public Entity(float x, float y, float z) {
		pos = new Vector(x, y, z);

	}

	public Entity(float x, float y, float z, float rotX, float rotY, float rotZ) {
		pos = new Vector(x, y, z, rotX, rotY, rotZ);

	}

	private Vector3f deltaPos = new Vector3f();

	public void increasePosition(float x, float y, float z, float rotX, float rotY, float rotZ) {
		deltaPos.set(0, 0, 0);

		pos.x += x;
		pos.y += y;
		pos.z += z;
		pos.rotX += rotX;
		pos.rotY += rotY;
		pos.rotZ += rotZ;

	}

	public abstract void tick();

	public abstract float getSpeed();

	public abstract HitBox getOriginHitBox();

	protected HitBox getHitBox(Vector pos, HitBox originHitbox) {
		return originHitbox.move(pos.x, pos.y, pos.z);
	}

}
