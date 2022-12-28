package xueli.mcremake.registry;

import org.lwjgl.utils.vector.Vector3d;
import xueli.game2.phys.aabb.AABB;
import xueli.game2.phys.aabb.NameableAABB;
import xueli.mcremake.core.block.BlockCollidable;
import xueli.mcremake.core.world.WorldAccessible;

import java.util.List;

public class BlockCollidableSolid implements BlockCollidable {

	public static final String AABB_NAME = "cg:solid_only";

	@Override
	public void getCollisionAABBs(int x, int y, int z, WorldAccessible world, List<AABB> aabbs) {
		aabbs.add(new AABB(new Vector3d(x, y, z), new Vector3d(x + 1, y + 1, z + 1)));
	}

	@Override
	public void getPickTestAABBs(int x, int y, int z, WorldAccessible world, List<NameableAABB> aabbs) {
		aabbs.add(new NameableAABB(AABB_NAME, new Vector3d(x, y, z), new Vector3d(x + 1, y + 1, z + 1)));
	}

}
