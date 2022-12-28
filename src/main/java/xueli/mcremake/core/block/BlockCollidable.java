package xueli.mcremake.core.block;

import xueli.game2.phys.aabb.AABB;
import xueli.mcremake.core.world.WorldAccessible;

import java.util.List;

public interface BlockCollidable {

	public void getAABBs(int x, int y, int z, WorldAccessible world, List<AABB> aabbs);

}
