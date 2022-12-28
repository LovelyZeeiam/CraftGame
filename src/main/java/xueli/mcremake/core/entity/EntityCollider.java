package xueli.mcremake.core.entity;

import org.lwjgl.utils.vector.Vector3i;
import org.lwjgl.utils.vector.Vector3d;
import xueli.game2.phys.aabb.AABB;
import xueli.mcremake.core.block.BlockCollidable;
import xueli.mcremake.core.block.BlockType;
import xueli.mcremake.core.world.WorldAccessible;

import java.util.ArrayList;
import java.util.List;

public class EntityCollider {

	private static final double EPS = 1.0e-5d;

	private final WorldAccessible world;
	// Relative to camera
	private final AABB relativeBox;

	public EntityCollider(AABB relativeEntityBox, WorldAccessible world) {
		this.world = world;
		this.relativeBox = relativeEntityBox;

	}

	public boolean collide(Vector3d original, Vector3d delta, Vector3d target) {
		ArrayList<AABB> aabbs = new ArrayList<>();
		AABB entityBox = this.relativeBox.add(original);
		addAllCollisionBox(this.world, entityBox.expand(delta), aabbs);

		double detY = delta.y;
		for (int i = 0; i < aabbs.size(); i++) {
			AABB box = aabbs.get(i);
			detY = this.collideClipY(entityBox, box, detY);
		}
		entityBox = entityBox.add(new Vector3d(0, detY, 0));

		double detX = delta.x;
		for (int i = 0; i < aabbs.size(); i++) {
 			AABB box = aabbs.get(i);
			detX = this.collideClipX(entityBox, box, detX);
		}
		entityBox = entityBox.add(new Vector3d(detX, 0, 0));

		double detZ = delta.z;
		for (int i = 0; i < aabbs.size(); i++) {
			AABB box = aabbs.get(i);
			detZ = this.collideClipZ(entityBox, box, detZ);
		}
//		entityBox = entityBox.add(new Vector3d(0, 0, detZ));

		target.x = detX;
		target.y = detY;
		target.z = detZ;
		return detX != delta.x || detY != delta.y || detZ != delta.z;
	}

	private double collideClipX(AABB movingBox, AABB toCollideBox, double movingDistance) {
		Vector3d m0 = movingBox.getVmin();
		Vector3d m1 = movingBox.getVmax();
		Vector3d c0 = toCollideBox.getVmin();
		Vector3d c1 = toCollideBox.getVmax();

		if(m1.y <= c0.y || m0.y >= c1.y)
			return movingDistance;
		if(m1.z <= c0.z || m0.z >= c1.z)
			return movingDistance;

		if (movingDistance > 0.0F && m1.x <= c0.x) {
			movingDistance = Math.min(movingDistance, c0.x - m1.x - EPS);
		}

		if (movingDistance < 0.0F && m0.x >= c1.x) {
			movingDistance = Math.max(movingDistance, c1.x - m0.x + EPS);
		}

		return movingDistance;
	}

	private double collideClipY(AABB movingBox, AABB toCollideBox, double movingDistance) {
		Vector3d m0 = movingBox.getVmin();
		Vector3d m1 = movingBox.getVmax();
		Vector3d c0 = toCollideBox.getVmin();
		Vector3d c1 = toCollideBox.getVmax();

		if(m1.x <= c0.x || m0.x >= c1.x)
			return movingDistance;
		if(m1.z <= c0.z || m0.z >= c1.z)
			return movingDistance;

		if (movingDistance > 0.0F && m1.y <= c0.y) {
			movingDistance = Math.min(movingDistance, c0.y - m1.y - EPS);
		}

		if (movingDistance < 0.0F && m0.y >= c1.y) {
			movingDistance = Math.max(movingDistance, c1.y - m0.y + EPS);
		}

		return movingDistance;
	}

	private double collideClipZ(AABB movingBox, AABB toCollideBox, double movingDistance) {
		Vector3d m0 = movingBox.getVmin();
		Vector3d m1 = movingBox.getVmax();
		Vector3d c0 = toCollideBox.getVmin();
		Vector3d c1 = toCollideBox.getVmax();

		if(m1.x <= c0.x || m0.x >= c1.x)
			return movingDistance;
		if(m1.y <= c0.y || m0.y >= c1.y)
			return movingDistance;

		if (movingDistance > 0.0F && m1.z <= c0.z) {
			movingDistance = Math.min(movingDistance, c0.z - m1.z - EPS);
		}

		if (movingDistance < 0.0F && m0.z >= c1.z) {
			movingDistance = Math.max(movingDistance, c1.z - m0.z + EPS);
		}

		return movingDistance;
	}

	public static void addAllCollisionBox(WorldAccessible world, AABB box, List<AABB> list) {
		Vector3i blockPosLittle = new Vector3i(box.getVmin());
		Vector3i blockPosBig = new Vector3i(box.getVmax());
		for (int x = blockPosLittle.x; x <= blockPosBig.x; x++) {
			for (int y = blockPosLittle.y; y <= blockPosBig.y; y++) {
				for (int z = blockPosLittle.z; z <= blockPosBig.z; z++) {
					BlockType block = world.getBlock(x, y, z);
					if(block == null) continue;
					BlockCollidable collidable = block.collidable();
					if(collidable == null) continue;
					collidable.getAABBs(x, y ,z, world, list);
				}
			}
		}
	}

}
