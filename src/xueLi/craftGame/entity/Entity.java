package xueLi.craftGame.entity;

import org.lwjgl.util.vector.Vector3f;

import xueLi.craftGame.block.Block;
import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.utils.HitBox;
import xueLi.craftGame.utils.Vector;
import xueLi.craftGame.world.Chunk;
import xueLi.craftGame.world.World;

public abstract class Entity {

	public Vector pos;
	public boolean isOnGround = false;
	public boolean isInLiquid = false;

	public Entity(float x, float y, float z) {
		pos = new Vector(x, y, z);

	}

	public Entity(float x, float y, float z, float rotX, float rotY, float rotZ) {
		pos = new Vector(x, y, z, rotX, rotY, rotZ);

	}

	private Vector3f deltaPos = new Vector3f();

	public void increasePosition(float x, float y, float z, float rotX, float rotY, float rotZ) {
		deltaPos.x += x;
		deltaPos.y += y;
		deltaPos.z += z;
		pos.rotX += rotX;
		pos.rotY += rotY;
		pos.rotZ += rotZ;
	}
	
	public void updatePos(World w) {
		HitBox originBox = getOriginHitBox();
		HitBox box = originBox.move(pos.x + deltaPos.x, pos.y + deltaPos.y, pos.z + deltaPos.z);
		
		pos.x += deltaPos.x;
		pos.y += deltaPos.y;
		pos.z += deltaPos.z;
		
		
		deltaPos.x = deltaPos.y = deltaPos.z = 0;

		

	}

	public abstract void tick();

	public abstract float getSpeed();

	public abstract HitBox getOriginHitBox();

	protected HitBox getHitBox(Vector pos, HitBox originHitbox) {
		return originHitbox.move(pos.x, pos.y, pos.z);
	}

}
