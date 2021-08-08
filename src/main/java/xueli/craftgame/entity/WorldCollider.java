package xueli.craftgame.entity;

import org.lwjgl.utils.vector.Vector3f;
import xueli.craftgame.world.Dimension;
import xueli.craftgame.world.Tile;
import xueli.game.physics.AABB;

public class WorldCollider {

	private Dimension world;

	public WorldCollider(Dimension dimension) {
		this.world = dimension;

	}

	public void entityCollide(Player e, Vector3f deltaPos) {
		e.pos.x += deltaPos.x;
		collide(e, new Vector3f(deltaPos.x, 0, 0));

		e.pos.y += deltaPos.y;
		collide(e, new Vector3f(0, deltaPos.y, 0));

		e.pos.z += deltaPos.z;
		collide(e, new Vector3f(0, 0, deltaPos.z));

	}

	private void collide(Player entity, Vector3f deltaPos) {
		AABB entityAabb = entity.getOriginAABB();

		for (int x = (int) Math.floor(entity.pos.x + entityAabb.getX0()); x < entity.pos.x + entityAabb.getX1(); x++) {
			for (int y = (int) Math.floor(entity.pos.y + entityAabb.getY0()); y < entity.pos.y
					+ entityAabb.getY1(); y++) {
				for (int z = (int) Math.floor(entity.pos.z + entityAabb.getZ0()); z < entity.pos.z
						+ entityAabb.getZ1(); z++) {
					Tile tile = world.getBlock(x, y, z);
					if (tile != null) {
						if (deltaPos.x > 0) {
							entity.pos.x = x + entityAabb.getX0();
						}
						if (deltaPos.x < 0) {
							entity.pos.x = x + entityAabb.getX1() + 1;
						}
						if (deltaPos.y > 0) {
							entity.pos.y = y - entityAabb.getY1();
							entity.speed.y = 0;
						}
						if (deltaPos.y < 0) {
							entity.pos.y = y - entityAabb.getY0() + 1;
							entity.speed.y = 0;
							entity.onGround = true;
						}
						if (deltaPos.z > 0) {
							entity.pos.z = z + entityAabb.getZ0();
						}
						if (deltaPos.z < 0) {
							entity.pos.z = z + entityAabb.getZ1() + 1;
						}

					}
				}
			}
		}

		// System.out.println(entity.pos.y);

	}

}
