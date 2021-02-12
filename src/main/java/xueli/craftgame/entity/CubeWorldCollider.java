package xueli.craftgame.entity;

import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.block.Tile;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;

public class CubeWorldCollider {

	private World world;

	public CubeWorldCollider(World world) {
		this.world = world;

	}

	/**
	 * 首先 达成效果 代码借鉴:
	 * https://github.com/Hopson97/MineCraft-One-Week-Challenge/blob/9bb62f2bb8f1d72b3dae7e4fbc8d067b8965c7da/Source/Player/Player.cpp
	 * TODO：当y的速度过大时，有可能会穿过方块掉落
	 */
	public void entityCollide(Entity e, Vector3f deltaPos) {
		e.pos.x += deltaPos.x;
		collide(e, new Vector3f(deltaPos.x, 0, 0));

		e.pos.y += deltaPos.y;
		collide(e, new Vector3f(0, deltaPos.y, 0));

		e.pos.z += deltaPos.z;
		collide(e, new Vector3f(0, 0, deltaPos.z));

	}

	private void collide(Entity entity, Vector3f deltaPos) {
		AABB entityAabb = entity.getOriginAABB();

		for (int x = (int) (entity.pos.x + entityAabb.getX0()); x < entity.pos.x + entityAabb.getX1(); x++) {
			for (int y = (int) (entity.pos.y + entityAabb.getY0()); y < entity.pos.y + entityAabb.getY1(); y++) {
				for (int z = (int) (entity.pos.z + entityAabb.getZ0()); z < entity.pos.z + entityAabb.getZ1(); z++) {
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
							entity.isOnGround = true;
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

	}

}
