package xueli.craftgame.client;

import xueli.craftgame.event.EventRequireChunk;
import xueli.craftgame.event.EventSyncTime;
import xueli.craftgame.world.SubChunk;
import xueli.craftgame.world.World;
import xueli.craftgame.world.WorldGenerator;

public class LocalWorldIO {

	private LocalTicker ctx;
	private World world;

	private WorldGenerator generator;

	public LocalWorldIO(LocalTicker ctx, World world) {
		this.ctx = ctx;
		this.world = world;

		this.generator = new WorldGenerator(world);

	}

	public void onRequireChunk(EventRequireChunk event) {
		SubChunk chunk = generator.genChunk(event.getX(), event.getZ());
		event.setValue(chunk);

	}

	// TODO: Should sync time per certain time
	public void onSyncTime(EventSyncTime event) {

	}
	
	public LocalTicker getContext() {
		return ctx;
	}
	
	public World getWorld() {
		return world;
	}
	
}
