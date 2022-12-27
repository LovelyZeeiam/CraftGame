package xueli.mcremake.classic.core.block;

import xueli.mcremake.classic.core.world.WorldAccessible;

public interface BlockListener {

	public void onLookAt(int x, int y, int z, WorldAccessible world);

	// TODO: On key listener

}
