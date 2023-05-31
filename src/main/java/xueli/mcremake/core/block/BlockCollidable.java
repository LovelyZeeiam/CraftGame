package xueli.mcremake.core.block;

import java.util.List;

import xueli.game2.phys.aabb.AABB;
import xueli.game2.phys.aabb.NameableAABB;
import xueli.mcremake.core.world.WorldAccessible;

public interface BlockCollidable {

	public static final BlockCollidable NONE = new BlockCollidable() {
		@Override
		public void getCollisionAABBs(int x, int y, int z, WorldAccessible world, List<AABB> aabbs) {
		}

		@Override
		public void getPickTestAABBs(int x, int y, int z, WorldAccessible world, List<NameableAABB> aabbs) {
		}
	};

	public void getCollisionAABBs(int x, int y, int z, WorldAccessible world, List<AABB> aabbs);

	public void getPickTestAABBs(int x, int y, int z, WorldAccessible world, List<NameableAABB> aabbs);

}
