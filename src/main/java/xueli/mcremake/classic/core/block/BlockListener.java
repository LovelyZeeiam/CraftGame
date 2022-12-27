package xueli.mcremake.classic.core.block;

import xueli.mcremake.classic.core.world.WorldAccessible;

public interface BlockListener {

	public static final BlockListener NONE = new BlockListener() {
		@Override
		public void onLookAt(int x, int y, int z, WorldAccessible world) {
		}
	};

	public void onLookAt(int x, int y, int z, WorldAccessible world);

	// TODO: On key listener

}
