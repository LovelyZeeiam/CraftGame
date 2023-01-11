package xueli.mcremake.core.block;

/**
 * Maybe later a division between client and server will be made,
 * or we create instance for every block instance
 */
public interface BlockListener {

	public static final BlockListener NONE = new BlockListener() {
	};

//	default public void onClientLookAt(int x, int y, int z, WorldAccessible world, PickResult pick) {}

//	default public void onClientMouseButton(int x, int y, int z, WorldAccessible world, PickResult pick) {}

	// TODO: On key listener

}
