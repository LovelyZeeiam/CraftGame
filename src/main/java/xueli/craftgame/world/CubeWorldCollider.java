package xueli.craftgame.world;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.block.Tile;
import xueli.craftgame.entity.Entity;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.utils.MathUtils;

public class CubeWorldCollider {

	private World world;

	public CubeWorldCollider(World world) {
		this.world = world;

	}

	public void entityCollide(Entity e, Vector3f deltaPos) {
		// 获取实体碰撞箱
		AABB entityAabb = e.getOriginAABB().move(e.pos.x, e.pos.y, e.pos.z);

		// 获取所有有价值检测碰撞的方块的碰撞箱
		ArrayList<AABB> aabbs = new ArrayList<AABB>();
		AABB expendedAabb = entityAabb.expand(deltaPos.x, deltaPos.y, deltaPos.z);
		int x0 = (int) expendedAabb.getX0();
		int x1 = (int) expendedAabb.getX1() + 1;
		int y0 = (int) expendedAabb.getY0();
		int y1 = (int) expendedAabb.getY1() + 1;
		int z0 = (int) expendedAabb.getZ0();
		int z1 = (int) expendedAabb.getZ1() + 1;

		for (int x = x0; x < x1; x++) {
			for (int y = y0; y < y1; y++) {
				for (int z = z0; z < z1; z++) {
					Tile tile = world.getBlock(x, y, z);
					if (tile != null) {
						ArrayList<AABB> tileAabbs = tile.getAabbs(tile.getParams(), world,x,y,z);
						for (AABB tileAabb : tileAabbs) {
							aabbs.add(tileAabb.move((float) x, (float) y, (float) z));
						}
					}
				}
			}
		}

		// 假设整个过程中时间为1 当与这么多个aabb碰撞时 最小的值就是我们需要让玩家停下来的值
		double time_when_player_should_stop = 1;

		double xd = deltaPos.x, yd = deltaPos.y, zd = deltaPos.z;
		a: for(AABB a : aabbs) {
			double x_time_when_x_collide = 0, x_time_when_x_leave = 1;

			// 在x轴方向上有碰撞时 x的位移
			double x_delta_when_x_collide = a.getX0() - entityAabb.getX1();
			// 在x轴方向上没有碰撞时 x的位移
			double x_delta_when_x_leave = a.getX1() - entityAabb.getX0();
			if(xd != 0) {
				// 则当x轴上发生碰撞时 此时的时间可能是以下两个区间的值
				x_time_when_x_collide = x_delta_when_x_collide / xd;
				x_time_when_x_leave = x_delta_when_x_leave / xd;
			} else {
				if((x_delta_when_x_collide < 0 && x_delta_when_x_leave < 0) || (x_delta_when_x_collide > 1 && x_delta_when_x_leave > 1)) {
					// x方向始终没有碰撞
					continue a;
				}
			}

			// 同理 在y轴方向有碰撞时
			double y_time_when_y_collide = 0, y_time_when_y_leave = 1;

			double y_delta_when_y_collide = a.getY0() - entityAabb.getY1();
			double y_delta_when_y_leave = a.getY1() - entityAabb.getY0();
			if(yd != 0) {
				y_time_when_y_collide = y_delta_when_y_collide / yd;
				y_time_when_y_leave = y_delta_when_y_leave / yd;
			} else {
				if((y_delta_when_y_collide < 0 && y_delta_when_y_leave < 0) || (y_delta_when_y_collide > 1 && y_delta_when_y_leave > 1)) {
					// y方向始终没有碰撞
					continue a;
				}
			}

			// 在z轴方向
			double z_time_when_z_collide = 0, z_time_when_z_leave = 1;

			double z_delta_when_z_collide = a.getZ0() - entityAabb.getZ1();
			double z_delta_when_z_leave = a.getZ1() - entityAabb.getZ0();
			if(zd != 0) {
				z_time_when_z_collide = z_delta_when_z_collide / zd;
				z_time_when_z_leave = z_delta_when_z_leave / zd;
			} else {
				if((z_delta_when_z_collide < 0 && z_delta_when_z_leave < 0) || (z_delta_when_z_collide > 1 && z_delta_when_z_leave > 1)) {
					// y方向始终没有碰撞
					continue a;
				}
			}

			// 取四个区间的交集
			double time_intersection_min = MathUtils.max(0, x_time_when_x_collide, y_time_when_y_collide, z_time_when_z_collide);
			double time_intersection_max = MathUtils.min(1, x_time_when_x_leave, y_time_when_y_leave, z_time_when_z_leave);

			time_when_player_should_stop = Math.min(time_intersection_min, time_when_player_should_stop);

		}
		
		System.out.println(time_when_player_should_stop);

		time_when_player_should_stop -= 0.01;
		
		e.pos.x += deltaPos.x * time_when_player_should_stop;
		e.pos.y += deltaPos.y * time_when_player_should_stop;
		e.pos.z += deltaPos.z * time_when_player_should_stop;

	}

}
